package com.car.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.baiduMapRoute.HttpRoute;
import com.car.bean.UserOrg;
import com.car.domain.Alarm;
import com.car.domain.StatInfo;
import com.car.domain.StopDeviceDetail;
import com.car.domain.main.DailyAlarm;
import com.car.domain.main.Distribution;
import com.car.domain.main.IncrementModel;
import com.car.influxdb.influxdbDao;
import com.car.redis.JedisUtil6800;
import com.car.service.CommonService;
import com.car.service.DeviceService;
import com.car.service.MonitorService;
import com.car.service.ReportService;
import com.car.setting.UserSetting;
import com.car.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/18  15:48
 * @Description
 */
@Service
public class Task {


    public static Logger logger = LoggerFactory.getLogger(Task.class);

    @Autowired
    MonitorService monitorService;

    @Autowired
    CommonService commonService;

    public static Random random = new Random(100);
    public static int SequenceNo = 8;


    /*
    更新经纬度  4/28
     */
//    @Scheduled(cron = "0 0/30 * * * ? ")
    public void updateLatLonToAddress() {
        List<AndroidMonitorTip> androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId("admin");
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            List<String> stringList = HttpRoute.getAddressByLocation(androidMonitorTip.getLat(), androidMonitorTip.getLng());
            androidMonitorTip.setAddress(stringList.get(0));
            androidMonitorTip.setProvince(stringList.get(1));
            androidMonitorTip.setCity(stringList.get(2));
            JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
        }
    }


    /*
    对每天上线设备天数增加
     */
    public static void onlineDays() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        androidMonitorTipList.forEach(androidMonitorTip -> {
            if (androidMonitorTip.isActivation()) {
                if (androidMonitorTip.isOnline()) {
                    String[] onlineDays = androidMonitorTip.getOnline_status().split(":");//OnLine:43:10:1:0
                    onlineDays[1] = Integer.parseInt(onlineDays[1]) + 1 + "";//天递增
                    onlineDays[2] = random.nextInt(50) + "";
                    onlineDays[3] = random.nextInt(50) + "";
                    androidMonitorTip.setOnline_status(onlineDays[0] + ":" + onlineDays[1] + ":" + onlineDays[2] + ":" + onlineDays[3] + ":" + onlineDays[4]);
                    JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSONObject.toJSONString(androidMonitorTip));
                }
            }
        });
    }


    /*
    获取数据 插入redis 4库  和influxdb
     */
//    @Scheduled(cron = "0 0 4/12 * * ?")
    public static void getDataToRedisAndIfluxdb() throws InterruptedException {
        List<AndroidMonitorTip> androidMonitorTips = JedisUtil6800.getAndroidMonitorTipsAll(4);
        Map<String, String> tags = new HashMap<String, String>();//索引
        Map<String, Object> fields = new HashMap<>();//字段值
        int count = 1;
        String measurement = "android_monitorTips_points";
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTips) {
            String deviceNumber = androidMonitorTip.getDev_number();
            String url = "http://183.196.51.30:53617/car/location?deviceNumber=" + deviceNumber;
            String success = null;
            try {
                success = HttpRoute.interfaceGET(url, "");
            } catch (Exception e) {
                System.out.println(androidMonitorTip.getDev_number() + "  失败   ");
                continue;
            }
            JSONObject jsonObject = JSON.parseObject(success);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject location = result.getJSONObject("location");
            String lng = location.getString("lng");
            String lat = location.getString("lat");
            String time = result.getString("time");

            androidMonitorTip.setLat(lat);
            androidMonitorTip.setLng(lng);

            androidMonitorTip.setLocation_time(time);
            androidMonitorTip.setReceive_time(time);
            androidMonitorTip.setLast_update_time(time);
            androidMonitorTip.setAlarm_time(time);
            androidMonitorTip.setStop_time(time);
            androidMonitorTip.setSpeed(random.nextInt(200) + "");
            androidMonitorTip.setLnglat(androidMonitorTip.getLng() + "," + androidMonitorTip.getLat());


            // 每次入库 +1
            androidMonitorTip.setSequenceNo(SequenceNo + "");

            JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
            tags.put("tagNum", androidMonitorTip.getDev_number());
            fields = influxdbDao.getFieldMapByAndroidMonitorTips(androidMonitorTip);

            influxdbDao.insert(measurement, tags, fields);

            System.out.println((count++) + "        ->       " + androidMonitorTip.getDev_number() + " 成功 ");
            tags.clear();
            Thread.sleep(30);// 耗时30分钟
        }
        SequenceNo++;
        logger.info("全体结束插入数据库");
    }


    /*
    不能用了
     */
    public static void getDataToRedis() {
        String devNum = "";
        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(devNum), AndroidMonitorTip.class);
        String url = "http://183.196.51.30:53617/car/location?deviceNumber=" + devNum;
        String success = null;
        try {
            success = HttpRoute.interfaceGET(url, "");
        } catch (Exception e) {
            System.out.println(androidMonitorTip.getDev_number() + "  失败   ");
        }
        JSONObject jsonObject = JSON.parseObject(success);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONObject location = result.getJSONObject("location");
        String lng = location.getString("lng");
        String lat = location.getString("lat");
        String time = result.getString("time");

        androidMonitorTip.setLat(lat);
        androidMonitorTip.setLng(lng);

        androidMonitorTip.setLocation_time(time);
        androidMonitorTip.setReceive_time(time);
        androidMonitorTip.setLast_update_time(time);
        androidMonitorTip.setAlarm_time(time);
        androidMonitorTip.setStop_time(time);
        androidMonitorTip.setSpeed(random.nextInt(200) + "");
        androidMonitorTip.setLnglat(androidMonitorTip.getLng() + "," + androidMonitorTip.getLat());
        // 每次入库 +1
        androidMonitorTip.setSequenceNo(SequenceNo + "");

        System.out.println(androidMonitorTip);
//        JedisUtil6500.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
    }

    /*
    轮询计算 设备在线数量   入9库 1个小时吧
    */
//    @Scheduled(cron = "0 0 0/1 * * ?")
    public void setDeviceStatusToUserorg() {
        StatInfo statInfo = new StatInfo();
        List<AndroidMonitorTip> androidMonitorTipList = new ArrayList<>();
        List<Integer> byList = new ArrayList<>();
//        List<UserOrg> userOrgList = JedisUtil6500.getUserOrgAll(9);
        String userId = "1584671979279729865";
        List<UserOrg> userOrgList1 = new ArrayList<>();
        userOrgList1.add(JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class));

        for (UserOrg userOrg : userOrgList1) {
            if (UserSetting.ADMINID.equals(userOrg.getUserName()) || UserSetting.QIANXI.equals(userOrg.getUserName())) {
                continue;
            }
            androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId(userOrg.getUser_id());
            byList = monitorService.getOnlineStatusByList(androidMonitorTipList);
            statInfo.setTotal(byList.get(0));
            statInfo.setOnline(byList.get(1));
            statInfo.setOffline(byList.get(2));
            statInfo.setAttention(byList.get(3));
            statInfo.setUnknown(byList.get(4));
            userOrg.setStatInfo(JSON.toJSONString(statInfo));
            JedisUtil6800.setString9(userOrg.getUser_id(), JSON.toJSONString(userOrg));
        }

        UserOrg admin = JSONObject.parseObject(JedisUtil6800.getString9("admin"), UserOrg.class);
        androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId("admin");
        byList = monitorService.getOnlineStatusByList(androidMonitorTipList);
        statInfo.setTotal(byList.get(0));
        statInfo.setOnline(byList.get(1));
        statInfo.setOffline(byList.get(2));
        statInfo.setAttention(byList.get(3));
        statInfo.setUnknown(byList.get(4));
        admin.setStatInfo(JSON.toJSONString(statInfo));
        JedisUtil6800.setString9("admin", JSON.toJSONString(admin));
        logger.info("全体设备 完毕 设备在线数量 计算 ");

    }

    /*
     轮询统计  有线-离线  无线-离线  无线-上线   报警统计    入库9库  6个小时把
     statistics
     */
    //    @Scheduled(cron = "0 0 0/1 * * ?")
    public static void setStatisticsToUserRedis() {
        CommonService commonService = new CommonService();
        List<Integer> WirelessOfflineList = new LinkedList<>();//无线下线
        List<Integer> WirelessOnlineList = new LinkedList<>();//无线上线
        List<Integer> WiredOfflineList = new LinkedList<>();//有线下线
        List<DailyAlarm> dailyAlarms = new ArrayList<>();


        List<IncrementModel> DevIncrementModelList = new LinkedList<>();//增量统计
        List<IncrementModel> ExpireDateModelList = new LinkedList<>();//到期统计
        List<IncrementModel> UserIncrementModelList = new LinkedList<>();//子用户增量统计


        int WirelessOffline = 0;
        int WirelessOnline = 0;
        int WiredOffline = 0;
        int DailyAlarm = 0;

        int DevIncrementModel = 0;
        int ExpireDateModel = 0;
        int UserIncrementModel = 0;

        List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);
//        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9("admin"), UserOrg.class);//admin

        for (UserOrg userOrg : userOrgList) {

            WirelessOfflineList = JSONArray.parseArray(userOrg.getWiredOffline(), Integer.class);
            WirelessOnlineList = JSONArray.parseArray(userOrg.getWirelessOnline(), Integer.class);
            WiredOfflineList = JSONArray.parseArray(userOrg.getWiredOffline(), Integer.class);
            dailyAlarms = JSON.parseArray(userOrg.getDailyAlarm(), com.car.domain.main.DailyAlarm.class);

            //暂时无用
//            DevIncrementModelList = JSON.parseArray(userOrg.getDevIncrementModel(), IncrementModel.class);
//            ExpireDateModelList = JSON.parseArray(userOrg.getExpireDateModel(), IncrementModel.class);
//            UserIncrementModelList = JSON.parseArray(userOrg.getUserIncrementModel(), IncrementModel.class);

            if (userOrg.getUserName().equals(UserSetting.QIANXI) || userOrg.getUserName().equals(UserSetting.ADMINID)) {
                continue;
            }

            List<AndroidMonitorTip> androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId(userOrg.getUser_id());
//            List<AndroidMonitorTip> androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId("admin");//admin
            for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
                if (androidMonitorTip.isActivation()) {//激活
                    System.out.println(androidMonitorTip.getDev_number() + "成功");
                    if (androidMonitorTip.isOnline()) {//在线
                        if (androidMonitorTip.getAlarm_type_id() != 0) {//如果存在报警
                            DailyAlarm++;
                        }

                        if (androidMonitorTip.getModel_type().equals("1") || androidMonitorTip.getModel_type().contains("1")) {//无线上线
                            WirelessOnline++;
                        }

                    } else {//离线
                        if (androidMonitorTip.getModel_type().equals("1")) {//无线下线
                            WirelessOffline++;
                        } else if (androidMonitorTip.getModel_type().equals("0")) {//有线下线
                            WiredOffline++;
                        }
                    }
                }
            }

            WiredOfflineList.remove(6);//先把最后的删除了
            WiredOffline = WiredOffline + random.nextInt(10);
            WiredOfflineList.add(0, WiredOffline);

            WirelessOfflineList.remove(6);
            WirelessOffline = WirelessOffline - random.nextInt(10);
            WirelessOfflineList.add(0, WirelessOffline);

            WirelessOnlineList.remove(6);
            WirelessOnline = WirelessOnline + random.nextInt(30);
            WirelessOnlineList.add(0, WirelessOnline);

            dailyAlarms.remove(4);
//            DailyAlarmList.remove(0);
            DailyAlarm = DailyAlarm + random.nextInt(10);
            dailyAlarms.add(0, new DailyAlarm(DailyAlarm, TimeUtils.getPreTimeByDay(-1, 1)));


            userOrg.setWiredOffline(JSON.toJSONString(WiredOfflineList));//
            userOrg.setWirelessOffline(JSON.toJSONString(WirelessOfflineList));//
            userOrg.setWirelessOnline(JSON.toJSONString(WirelessOnlineList));//

            userOrg.setDailyAlarm(JSON.toJSONString(dailyAlarms));

            JedisUtil6800.setString9(userOrg.getUser_id(), JSON.toJSONString(userOrg));
//        JedisUtil6800.setString9("admin", JSON.toJSONString(userOrg));//admin


            System.out.println("OVER");
            WiredOffline = 0;
            WirelessOffline = 0;
            WirelessOnline = 0;
            DailyAlarm = 0;
        }
    }

    public static void main(String[] args) {
        onlineDays();
//        setStatisticsToUserRedis();
    }


    /*
    轮询计算 设备地理位置  入库  6个小时吧
     */
//    @Scheduled(cron = "0 0 0/2 * * ?")
    public void setDeviceDistributionToUserorg() {
        List<Distribution> distributions = null;
        List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);
        for (UserOrg user : userOrgList) {
            if (UserSetting.ADMINID.equals(user.getUserName()) || UserSetting.QIANXI.equals(user.getUserName())) {
                continue;
            }
            distributions = getDistributionByLocations(user.getUser_id());
            user.setDistributions(JSON.toJSONString(distributions));
            JedisUtil6800.setString9(user.getUser_id(), JSON.toJSONString(user));
        }

        UserOrg admin = JSONObject.parseObject(JedisUtil6800.getString9("admin"), UserOrg.class);
        distributions = getDistributionByLocations("admin");
        admin.setDistributions(JSON.toJSONString(distributions));
        JedisUtil6800.setString9("admin", JSON.toJSONString(admin));
        logger.info("全体设备 完毕 地理位置数量计算 ");

    }

    public List<Distribution> getDistributionByLocations(String userId) {
        List<AndroidMonitorTip> androidMonitorTipList = null;
        if (UserSetting.ADMINID.equals(userId)) {
            androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        } else {
            androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId(userId);
        }
        List<Distribution> distributionList = new ArrayList<>();
        for (AndroidMonitorTip monitorTips : androidMonitorTipList) {
            String province = monitorTips.getProvince();
            int AdCode = DeviceService.getAdCodeByProvinceName(province);
            Distribution distribution = new Distribution(AdCode, 1);
            distributionList.add(distribution);
        }
        Map<Integer, Integer> integerHashMap = DeviceService.getProvinceSunByDistributionList(distributionList);
        distributionList.clear();
        for (Map.Entry entry : integerHashMap.entrySet()) {
            Distribution distribution = new Distribution((Integer) entry.getKey(), (Integer) entry.getValue());
            distributionList.add(distribution);
        }
        return distributionList;
    }


    /*
    轮询  入库5库   一天一次吧  模拟记录设备报警记录
     */
//    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void setDeviceAlarms() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        List<Alarm> alarmList = new ArrayList<>();
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            String devNum = androidMonitorTip.getDev_number();
            if (androidMonitorTip.isActivation()) {
                if (random.nextBoolean()) {
                    Alarm alarm = new Alarm();
                    BeanUtils.copyProperties(androidMonitorTip, alarm);
                    alarm.setAlarm_type_id(random.nextInt(48) + 1);// 从1-49
                    alarm.setAlarm_text(ReportService.getAlarmNameByAlarmType(alarm.getAlarm_type_id() + ""));
                    alarmList = JSON.parseArray(JedisUtil6800.getString5(devNum), Alarm.class);
                    alarmList.add(alarm);
                    JedisUtil6800.setString5(androidMonitorTip.getDev_number(), JSON.toJSONString(alarmList));
                    alarmList.clear();
                }
            }
        }
        logger.info("设备 结束 插入 报警");

    }


    /*
     轮询  入库3库  一天一次 模拟设备停留详情
     */
//    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void setStopDeviceDetailList() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        List<StopDeviceDetail> stopDeviceDetailList = new ArrayList<>();

        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            if (androidMonitorTip.isActivation()) {
                if (random.nextBoolean()) {
                    int days = random.nextInt(10);
                    StopDeviceDetail stopDeviceDetail = new StopDeviceDetail();
                    BeanUtils.copyProperties(androidMonitorTip, stopDeviceDetail);
                    stopDeviceDetail.setStart_time(TimeUtils.getPreTimeByDay(-days, 1));
                    stopDeviceDetail.setEnd_time(TimeUtils.getGlnz());
                    stopDeviceDetail.setStop_minutes(days * UserSetting.MINUTES + "");

                    stopDeviceDetailList = JSON.parseArray(JedisUtil6800.getString3(androidMonitorTip.getDev_number()), StopDeviceDetail.class);
                    stopDeviceDetailList.add(stopDeviceDetail);
                    JedisUtil6800.setString3(androidMonitorTip.getDev_number(), JSON.toJSONString(stopDeviceDetailList));
                    stopDeviceDetailList.clear();
                }
            }
        }
        logger.info("设备 结束 插入停留时间 天");

    }


}
