package com.car.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.baiduMapRoute.HttpRoute;
import com.car.bean.UserOrg;
import com.car.domain.StatInfo;
import com.car.domain.main.Distribution;
import com.car.redis.JedisUtil6800;
import com.car.service.CommonService;
import com.car.service.DeviceService;
import com.car.service.MonitorService;
import com.car.setting.UserSetting;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/20  10:44
 * @Description
 */
class TaskTest {

    public static Logger logger = LoggerFactory.getLogger(Task.class);

    public static Random random = new Random(100);

    public static int SequenceNo = 7;

    @Test
    void setDeviceStatusToUserorg() {
        CommonService commonService = new CommonService();
        MonitorService monitorService = new MonitorService();
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
            System.out.println(userOrg);
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

//        UserOrg admin = JSONObject.parseObject(JedisUtil6500.getString9("admin"), UserOrg.class);
//        androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId("admin");
//        byList = monitorService.getOnlineStatusByList(androidMonitorTipList);
//        statInfo.setTotal(byList.get(0));
//        statInfo.setOnline(byList.get(1));
//        statInfo.setOffline(byList.get(2));
//        statInfo.setAttention(byList.get(3));
//        statInfo.setUnknown(byList.get(4));
//        admin.setStatInfo(JSON.toJSONString(statInfo));
//        JedisUtil6500.setString9("admin", JSON.toJSONString(admin));
//        logger.info("全体设备 完毕 设备在线数量 计算 ");
    }


    @Test
    void setDeviceDistributionToUserorg() {
        CommonService commonService = new CommonService();
        MonitorService monitorService = new MonitorService();
        List<Distribution> distributions = null;
//        List<UserOrg> userOrgList = JedisUtil6500.getUserOrgAll(9);

        String userId = "1584671979279729865";
        List<UserOrg> userOrgList1 = new ArrayList<>();
        userOrgList1.add(JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class));

        for (UserOrg user : userOrgList1) {
            if (UserSetting.ADMINID.equals(user.getUserName()) || UserSetting.QIANXI.equals(user.getUserName())) {
                continue;
            }
            distributions = getDistributionByLocations(user.getUser_id(), commonService);
            user.setDistributions(JSON.toJSONString(distributions));
            JedisUtil6800.setString9(user.getUser_id(), JSON.toJSONString(user));
        }

//        UserOrg admin = JSONObject.parseObject(JedisUtil6500.getString9("admin"), UserOrg.class);
//        distributions = getDistributionByLocations("admin");
//        admin.setDistributions(JSON.toJSONString(distributions));
//        JedisUtil6500.setString9("admin", JSON.toJSONString(admin));
//        logger.info("全体设备 完毕 地理位置数量计算 ");
    }

    public List<Distribution> getDistributionByLocations(String userId, CommonService commonService) {
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

    @Test
    void getDataToRedis() {
        String devNum = "161117090700009";
        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(devNum), AndroidMonitorTip.class);
        String url = "http://183.196.51.30:53617/car/location?deviceNumber=" + devNum;
        String success = null;
        try {
            success = HttpRoute.interfaceGET(url, "");
        } catch (Exception e) {
            System.out.println(androidMonitorTip.getDev_number() + "  失败   ");
            return;
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












}