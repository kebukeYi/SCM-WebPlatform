package com.car.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.Route;
import com.car.annontation.SaveCmd;
import com.car.baiduMapRoute.HttpRoute;
import com.car.domain.*;
import com.car.influxdb.influxdbDao;
import com.car.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.car.androidbean.AndroidMonitorTip;
import com.car.dto.DeviceDTO;
import com.car.dto.DeviceDTOs;
import com.car.redis.JedisUtil6800;
import com.car.setting.CtrlSetting;
import com.car.setting.UserSetting;
import com.car.utils.TimeUtils;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:52
 * @Description
 */
@Service
public class MonitorService {

    @Autowired
    UserService userService;

    @Autowired
    CommonService commonService;

    static Random random = new Random();
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static SimpleDateFormat formatGlz = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    /**
     * /monitor/ShowMonitorDeviceList    3/8 22:23 已更换
     */
    public DeviceDTOs getGroupIdOrCataIdDeviceByMap(Map<String, Object> map) {
        List<DeviceDTO> deviceDTOList = null;
        List<AndroidMonitorTip> androidMonitorTipList = null;
        String Flag = map.get("Flag") + "";//在线与否标志位
        String userId = (String) map.get("userId");//1-100
//        String orgOrCataId = map.get("OrgId");// G101    C1    U1
        androidMonitorTipList = userService.getAndroidMonitorTipsByUserIdOrOrgIdOrCataId(map);
        deviceDTOList = getAndroidMonitorTipsByFlag(androidMonitorTipList, map);
        int length = deviceDTOList.size();
        System.out.println("用户  " + userId + "  下所有 : " + Flag + " :设备大小：" + length);
        int start = 0;
        int page = 1;
        start = Integer.parseInt(map.get("start") + "");
        page = Integer.parseInt(map.get("page") + "");
        int end = page * UserSetting.LIMIT;
//        end = end > length ? length - 1 : end;
        end = end > length || end == length ? length - 1 : end;
//todo
        deviceDTOList = deviceDTOList.subList(start, end + 1);
        DeviceDTOs deviceDTOs = new DeviceDTOs();
        String key = (String) map.get("Key");
        System.out.println("Key: " + key);
        if (key != null && key.length() > 0) {
            System.out.println("进入搜索功能");
            AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(key);
            if (androidMonitorTip == null) {
                return null;
            }
            DeviceDTO deviceDTO = new DeviceDTO();
            if (androidMonitorTip.isOnline()) {
                deviceDTO.setOnline(true);
                deviceDTO.setStatus(TimeUtils.getStopAndOffLine().get(0));
            } else {
                deviceDTO.setOnline(false);
                deviceDTO.setStatus(TimeUtils.getStopAndOffLine().get(1));
            }
            BeanUtils.copyProperties(androidMonitorTip, deviceDTO);
            deviceDTOList.clear();
            deviceDTOList.add(deviceDTO);
        }

        deviceDTOs.setDeviceDTOList(deviceDTOList);
        List<Integer> byList = getOnlineStatusByList(androidMonitorTipList);//筛选在线  监控页面左下角
        StatInfo statInfo = new StatInfo();
        statInfo.setTotal(byList.get(0));
        statInfo.setOnline(byList.get(1));
        statInfo.setOffline(byList.get(2));
        statInfo.setAttention(byList.get(3));
        statInfo.setUnknown(0);
        deviceDTOs.setStatInfo(statInfo);
        return deviceDTOs;
    }


    /**
     * 暂时弃用
     * DeviceNumber: "181219310018619"
     * PageSize: 100
     * StartTime: "2020-01-10 11:03:46"
     * EndTime: "2020-02-05 17:49:55"
     * WithBS: true
     * WithStop: true
     * StopMinutes: "5"
     * MapType: 0
     * monitor/ShowHistory   稍后
     * Web 界面
     */
    public Historys getHistorysByNumAndTime(Map<String, Object> map) throws ParseException {
//        List<MonitorTips> monitorTipsList = JedisUtil6491.getMonitorTipsAll(8);
//        List<AndroidMonitorTip> monitorTipsList = influxdbDao.queryAndroidMonitorTipsByTime(map);//不在查influxdb 库

        String startTime = (String) map.get("StartTime");
        Date webStartTime = format.parse(startTime);
        String endTime = (String) map.get("EndTime");
        Date webEndTime = format.parse(endTime);

        System.out.println("webStartTime " + webStartTime + "  webEndTime " + webEndTime);
        String dev_number = (String) map.get("DeviceNumber");
        Historys historys = new Historys();
        List<History> historyList = new ArrayList<>();
        //[30.082525,103.85656, 30.098705,104.34326, 30.114883,104.82997, 30.131063,105.31667, 30.147243,105.803375, 30.163422,106.290085, 30.179602,106.77679, 30.195782,107.26349, 30.21196,107.75019, 30.22814,108.2369, 30.24432,108.7236, 30.260498,109.210304, 30.276678,109.697014, 30.292858,110.183716, 30.309036,110.67042, 30.325216,111.15712, 30.341396,111.64383, 30.357574,112.13053, 30.373755,112.61723, 30.389935,113.10394, 30.406113,113.590645, 30.422293,114.07735, 30.438473,114.56406, 30.45465,115.05076, 30.47083,115.53746, 30.487011,116.02417, 30.50319,116.51087, 30.51937,116.997574, 30.53555,117.484276, 30.551727,117.970985, 30.567907,118.45769, 30.567907,118.45769, 30.567907,118.45769]
        List<String> lnglatList = JedisUtil6800.getAndroidMonitorTipsLngLatArrayLisByDeviceId(dev_number);
        List<String> locationTimeList = JedisUtil6800.getAndroidMonitorTipsLocationTimeListByDeviceId(dev_number);
        if (lnglatList.size() == 0) {
            historys.setData(new ArrayList<History>());
            return historys;
        }
        int length = lnglatList.size();
        int end = length - 1;
        for (int i = 0; i < length; i++) {

            Date redisStartDate = formatGlz.parse(locationTimeList.get(i));

            Date redisEndDate = formatGlz.parse(locationTimeList.get(end));

            while (!redisEndDate.before(webEndTime)) {
                end--;
                redisEndDate = formatGlz.parse(locationTimeList.get(end));
            }


            if (redisStartDate.after(webStartTime)) {
                if (i > end) {
                    break;
                }

                String lat = lnglatList.get(i).split(",")[0];
                String lng = lnglatList.get(i).split(",")[1];

                History history = new History();
                history.setStatus("NotDisassembly, ContinueLocation, ACCOFF, OilON, EleON");
                history.setLat(lat);
                history.setLng(lng);
                history.setDev_number(dev_number);
                history.setSpeed(random.nextInt(80) + 20 + "");
                history.setLocation_time(format.format(redisStartDate));//模拟时间
                historyList.add(history);
            }
        }
        System.out.println("共有轨迹记录 ：" + historyList.size() + " 条");
        historys.setData(historyList);
        historys.setMaxDateTime(null);
        return historys;
    }


    /**
     * 从Influxdb 中获取数据
     */
    public Historys getHistorysByNumAndTimeFormInfluxdb(Map<String, String> map) throws ParseException {
        String startTime = map.get("StartTime");
        Date webStartTime = format.parse(startTime);
        String endTime = map.get("EndTime");
        Date webEndTime = format.parse(endTime);
        System.out.println("webStartTime " + webStartTime + "  webEndTime " + webEndTime);
        String dev_number = map.get("DeviceNumber");
        Historys historys = new Historys();
        List<MonitorTips> monitorTipsList = influxdbDao.queryMonitorTips(map);
        List<History> historyList = new ArrayList<>(monitorTipsList.size());
        for (MonitorTips monitorTips : monitorTipsList) {
            History history = new History();
            history.setDev_number(monitorTips.getDeviceNumber());
            history.setLat(monitorTips.getLat());
            history.setLng(monitorTips.getLng());
            history.setLocation_time(TimeUtils.millis2Glz(monitorTips.getLocationTime()));
            history.setLocation_type(monitorTips.getLocationType());
        }
        historys.setData(historyList);
        historys.setMaxDateTime(null);
        return historys;
    }


    /**
     * monitor/ShowMonitorTips     3/8 22:23 已更换
     */
    public AndroidMonitorTip getMonitorByNum(Map<String, Object> map) {
        String DeviceNumber = (String) map.get("DeviceNumber");
        String Maptype = map.get("MapType") + "";
        if (DeviceNumber == null) {
            return null;
        }
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(DeviceNumber);
        List<String> stringList = null;
        try {
            System.out.println(androidMonitorTip.getLng()+"  :  "+androidMonitorTip.getLat());
            stringList = HttpRoute.getAddressByLocation(androidMonitorTip.getLat(), androidMonitorTip.getLng());
        } catch (Exception e) {
            return androidMonitorTip;
        }
        System.out.println(stringList);
        androidMonitorTip.setAddress(stringList.get(0));
        androidMonitorTip.setProvince(stringList.get(1));
        androidMonitorTip.setCity(stringList.get(2));
        return androidMonitorTip;
    }


    /**
     * monitor/ShowMonitorTipLocation   3/8 22:23 已更换
     */
    public DeviceDTO getMontiorById(String id) {
        if (!"".equals(id)) {
            DeviceDTO deviceDTO = new DeviceDTO();
            AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(id);
            BeanUtils.copyProperties(androidMonitorTip, deviceDTO);
            return deviceDTO;
        }
        return null;
    }

    /**
     * limit: 15
     * start: 0
     * page: 1
     * DeviceNumber: "1111100019"
     * /monitor/ShowAlarm   3/8 17:44 已更换
     */
    public Alarms getAlarmsById(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        String DeviceNumber = (String) map.get("DeviceNumber");//报警历史信息
        List<Alarm> alarmList = null;
        Alarms alarms = new Alarms();
        if (DeviceNumber != null) {//单独设备报警记录
            //todo 应该是 设备历史 报警列表
            alarmList = JedisUtil6800.getAlarmAndroidMonitorTipsByDeviceId(DeviceNumber);
            if (alarmList == null) {
                alarms.setData(new ArrayList<>());//空list
                return alarms;
            }
        } else {// 默认 用户的所有设备报警记录
            //todo 这个得轮询计算
            alarmList = commonService.getAlarmAndroidMonitorTipsByUserId(userId);
        }

        int length = alarmList.size();
        System.out.println("用户  报警记录   数量  " + length);

        int start = 0;
        int page = 1;
        start = Integer.parseInt(map.get("start") + "");
        page = Integer.parseInt(map.get("page") + "");
        int end = page * UserSetting.LIMIT;

//        end = end > length ? length - 1 : end;
        end = end > length || end == length ? length - 1 : end;
//todo
        alarmList = alarmList.subList(start, end + 1);


        alarms.setLimit(UserSetting.LIMIT);
        alarms.setPage(page);
        alarms.setIsExcel(false);
        alarms.setTotal(length);
        alarms.setData(alarmList);
        return alarms;
    }

    /**
     * sends 指令集
     * /monitor/Sends
     * {"cmdInfos":["361054524214541","484459121697141","827123617674248","724165141205423","567568549603653","468562278189422","514149823740513","257425461946757","423957194675831","922959618530200","864488027662263","782610614424901","913202711876357","194427633878252","428500942274088","208854239813793","646544179645335","499749637391574","269003381805746","162206468891715","727583023385682","678426151389192","340389157906286","194103787340023","232881574657857","355453025697238","651422550534638","188912540584521","424722074820603","473161714011333","167512418717503"],
     * "sendbody":{"CmdKey":"BP07","Minutes":"30"}}
     */
    public boolean sendCtrlByDeviceNums(JSONObject jsonObject) {
        JSONArray cmdInfos = jsonObject.getJSONArray("cmdInfos");
        Map<String, String> map = new HashMap<>();
        return true;
    }

    /**
     * /monitor/Send   3/8 17:44 已更换
     * /monitor/Send {"cmdInfos":["484459121697141"],"sendbody":{"CmdKey":"BP07","DeviceNumber":"484459121697141","Minutes":"30"}}
     * 处理指令
     */
    @SaveCmd
    public boolean sendCtrlByDeviceNum(JSONObject jsonObject) {
        System.out.println("/monitor/Send  " + jsonObject.toString());
        boolean result = true;
        JSONObject sendbody = jsonObject.getJSONObject("sendbody");//指令体
        String CmdKey = sendbody.getString("CmdKey");
        String userId = jsonObject.getString("userId");
//        String DeviceNumber = map.get("DeviceNumber");
        JSONArray cmdInfos = jsonObject.getJSONArray("cmdInfos");
        for (Object DeviceNumber : cmdInfos) {
            DeviceNumber = DeviceNumber + "";
            CmdRecord cmdRecord = new CmdRecord();
            if (CtrlSetting.BP02.equals(CmdKey)) {
                String Defence = sendbody.getString("Defence");
                String CmdName = Defence.equals("1") ? "撤防" : "设防";
                cmdRecord.setCmdName("远程设防/撤防");
                cmdRecord.setMessage(CmdName);
            } else if (CtrlSetting.BP74.equals(CmdKey)) {
                String Duration = sendbody.getString("Duration");//持续时间
                String Speed = sendbody.getString("Speed");//超速速度
                cmdRecord.setDuration(Duration);
                cmdRecord.setSpeed(Speed);
                cmdRecord.setMessage("持续 : " + Duration + "秒;超速速度 : " + Speed + "km/h");
                cmdRecord.setCmdName("远程设置最高速度");
                cmdRecord.setResultText("等待处理");
            } else if (CtrlSetting.BP07.equals(CmdKey)) {//设置上发时间间隔
                //monitor/Send {CmdKey=BP07, DeviceNumber=827123617674248, Minutes=60, userId=1}//是DeviceNumber
                System.out.println("  BP07  " + jsonObject.toString());
                String Minutes = sendbody.getString("Minutes");
                String command = CtrlService.creatBP07(Minutes);
                cmdRecord.setCmdName("设置上发时间间隔");
                cmdRecord.setMessage(Minutes + "分钟");
                cmdRecord.setMinutes(Minutes);
                JedisUtil6800.setString11(DeviceNumber + ":" + CtrlSetting.BP07 + ":" + Minutes, command);
            } else if (CtrlSetting.BP61.equals(CmdKey)) {//设备重启
                //{CmdKey=BP61, DeviceNumber=484459121697141, userId=1}
                cmdRecord.setCmdName("设备重启");
                cmdRecord.setMessage("重启");
                String command = CtrlService.createBP61();
                JedisUtil6800.setString11(DeviceNumber + ":" + CtrlSetting.BP61, command);
            } else if (CtrlSetting.BP62.equals(CmdKey)) {//恢复出厂设置
                cmdRecord.setCmdName("恢复出厂设置");
                cmdRecord.setMessage("出厂设置");
                String command = CtrlService.createBP62();
                JedisUtil6800.setString11(DeviceNumber + ":" + CtrlSetting.BP62, command);
            } else if (CtrlSetting.BP84.equals(CmdKey)) {//设置工作模式
                //  /monitor/Send  {CmdKey=BP84, DeviceNumber=484459121697141, Flag=01, userId=1}
                cmdRecord.setCmdName("设置工作模式");
                String flag = sendbody.getString("Flag");
                String message = "";
                if (flag.equals("00")) {
                    message = "不设置工作模式";
                } else if (flag.equals("01")) {
                    message = "设置正常模式";
                } else if (flag.equals("02")) {
                    message = "连续定位";
                } else if (flag.equals("03")) {
                    message = "智能监控";
                }
                cmdRecord.setMessage(message);
                String command = CtrlService.createBP84(flag);
                JedisUtil6800.setString11(DeviceNumber + ":" + CtrlSetting.BP84, command);
            } else if (CtrlSetting.BP85.equals(CmdKey)) {//设置防拆报警开关
//       /monitor/Send  {CmdKey=BP85, DeviceNumber=484459121697141, Flag=1, userId=1}
                String flag = sendbody.getString("Flag");
                String command = CtrlService.createBP85(flag);
                cmdRecord.setCmdName("设置防拆报警开关");
                String message = flag.equals("1") ? "打开" : "关闭";
                cmdRecord.setMessage(message);
                JedisUtil6800.setString11(DeviceNumber + ":" + CtrlSetting.BP85, command);
            } else if (CtrlSetting.BP64.equals(CmdKey)) {//更改 IP 或域名
//monitor/Send  {CmdKey=BP64, DeviceNumber=484459121697141, Domain=d, Port=44, userId=1}
                String ip = sendbody.getString("Domain");
                String port = sendbody.getString("Port");
                cmdRecord.setCmdName("设置IP/PORT");
                cmdRecord.setMessage("IP:" + ip + "  PORT:" + port);
                String command = CtrlService.createBP64(ip, port);
                JedisUtil6800.setString11(DeviceNumber + ":" + CtrlSetting.BP64 + ":" + ip + "," + port, command);
            } else if (CtrlSetting.BP88.equals(CmdKey)) {//设置数据上传时间点
//monitor/Send  {CmdKey=BP88, DeviceNumber=484459121697141, TimeStamp=052001, userId=1}
                String TimeStamp = sendbody.getString("TimeStamp");
                cmdRecord.setMessage(TimeStamp);
                cmdRecord.setCmdName("设置数据上传时间点");
                String command = CtrlService.createBP88(TimeStamp);
                JedisUtil6800.setString11(DeviceNumber + ":" + CtrlSetting.BP88 + ":" + TimeStamp, command);
            } else if (CtrlSetting.BP78.equals(CmdKey)) {//主控号码
                String PhoneNumber = sendbody.getString("PhoneNumber");
                cmdRecord.setMessage(PhoneNumber);
                cmdRecord.setCmdName("设置主控号码");
                String command = CtrlService.createBP78(PhoneNumber);
                JedisUtil6800.setString11(DeviceNumber + ":" + CtrlSetting.BP78 + ":" + PhoneNumber, command);
            }

            List<CmdRecord> cmdRecordList = JSON.parseArray(JedisUtil6800.getString10(DeviceNumber + ""), CmdRecord.class);
            if (cmdRecordList == null) {
                cmdRecordList = new ArrayList<>();
            }
            cmdRecord.setOperator(UserService.userMap.get(userId).getUserName());
            cmdRecord.setCmdKey(CmdKey);
            cmdRecord.setCmdStatus(2);
            cmdRecord.setCmdStatusText("下发成功");
            cmdRecord.setDeviceName(DeviceNumber + "");
            cmdRecord.setLastSendTime(TimeUtils.getGlnz());
            cmdRecordList.add(cmdRecord);
            cmdRecord.setResultText("等待处理");
            JedisUtil6800.setString10(DeviceNumber + "", JSON.toJSONString(cmdRecordList));
        }
        return result;
    }

    /**
     * limit: 15
     * start: 0
     * page: 1
     * DeviceNumber: "181219310018261"
     * /monitor/ShowCmdRecord   3/8 17:44 已更换
     */
    public CmdRecords getCmdRecordsByMap(Map<String, Object> map) {
        CmdRecords cmdRecords = new CmdRecords();
        String DeviceNumber = (String) map.get("DeviceNumber");
        List<CmdRecord> cmdRecordList = JSON.parseArray(JedisUtil6800.getString10(DeviceNumber), CmdRecord.class);
        if (cmdRecordList == null) {
            cmdRecords.setData(new ArrayList<>());
            return cmdRecords;
        }
        int length = cmdRecordList.size();
        int start = 0;
        int page = 1;
        start = Integer.parseInt( ""+map.get("start"));
        page = Integer.parseInt(""+map.get("page"));
        int end = page * UserSetting.LIMIT;
        end = end > length || end == length ? length - 1 : end;
        //todo
        cmdRecordList = cmdRecordList.subList(start, end + 1);
        cmdRecords.setData(cmdRecordList);
        cmdRecords.setLimit(UserSetting.LIMIT);
        cmdRecords.setPage(page);
        cmdRecords.setTotal(cmdRecordList.size());
        return cmdRecords;
    }

    /**
     * /Monitor/UpdateAttention   3/8 17:44 已更换
     */
    public boolean UpdateAttentionByNum(Map<String, Object> map) {
        String DeviceNumber = (String) map.get("DeviceNumber");
        boolean IsAttention = (boolean) map.get("IsAttention");
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(DeviceNumber);
        if (IsAttention) {
            if (androidMonitorTip != null) {
                System.out.println("DeviceNumber :  " + DeviceNumber + " 设置了关注");
                androidMonitorTip.setAttention(true);
            }
        } else {
            if (androidMonitorTip != null) {
                androidMonitorTip.setAttention(false);
            }
        }
        JedisUtil6800.setString4(DeviceNumber, JSON.toJSONString(androidMonitorTip));
        return true;
    }


    /**
     * ShowCmdList  已更改
     */
    public static List<Cmd> ShowCmdList() {
        List<Cmd> cmds = new ArrayList<>();
//        cmds.add(new Cmd(1, "远程设防/撤防", "BP02", 0, false));
        cmds.add(new Cmd(3, "定时监控间隔", "BP07", 0, false));// {CmdKey=BP07, DeviceNumber=827123617674248, Minutes=60, userId=1}
        cmds.add(new Cmd(4, "设备重启", "BP61", 0, false));// {CmdKey=BP07, DeviceNumber=827123617674248, Minutes=60, userId=1}
        cmds.add(new Cmd(5, "恢复出厂设置", "BP62", 0, false));// {CmdKey=BP07, DeviceNumber=827123617674248, Minutes=60, userId=1}
        cmds.add(new Cmd(6, "更改 IP 或域名", "BP64", 0, false));// {CmdKey=BP07, DeviceNumber=827123617674248, Minutes=60, userId=1}
//        cmds.add(new Cmd(8, "远程设置最高速度", "BP74", 0, false));
        cmds.add(new Cmd(9, "设置工作模式", "BP84", 0, false));
        cmds.add(new Cmd(10, "设置数据上传时间点", "BP88", 0, false));
        cmds.add(new Cmd(11, "主控号码", "BP78", 0, false));
        cmds.add(new Cmd(12, "防拆开关设置开关", "BP85", 0, false));
        return cmds;
    }


    /**
     * 3/8 22:27  已更换
     */
    public List<DeviceDTO> getAndroidMonitorTipsByFlag(List<AndroidMonitorTip> monitorTipsList, Map<String, Object> map) {
        String Flag = map.get("Flag") + "";
        String orgOrCataId = (String) map.get("OrgId");
        List<DeviceDTO> deviceDTOList = new ArrayList<>();
        for (AndroidMonitorTip monitorTips : monitorTipsList) {
            if (UserSetting.DEFAULT.equals(Flag)) {//默认是所有设备
                DeviceDTO deviceDTO = new DeviceDTO();
                BeanUtils.copyProperties(monitorTips, deviceDTO);
                deviceDTOList.add(deviceDTO);
            } else {
                if (!monitorTips.isDisabled()) {//是否未过期
                    if (UserSetting.ONLINE.equals(Flag)) {//在线
                        if (monitorTips.isOnline()) {
                            DeviceDTO deviceDTO = new DeviceDTO();
                            BeanUtils.copyProperties(monitorTips, deviceDTO);
                            deviceDTOList.add(deviceDTO);
                        }
                    } else if (UserSetting.OFFLINE.equals(Flag)) {//离线
                        if (!monitorTips.isOnline()) {
                            if (monitorTips.isActivation()) {
                                DeviceDTO deviceDTO = new DeviceDTO();
                                BeanUtils.copyProperties(monitorTips, deviceDTO);
                                deviceDTOList.add(deviceDTO);
                            }
                        }
                    } else if (UserSetting.ATTENTION.equals(Flag)) {//关注
                        if (monitorTips.isAttention()) {
                            DeviceDTO deviceDTO = new DeviceDTO();
                            BeanUtils.copyProperties(monitorTips, deviceDTO);
                            deviceDTOList.add(deviceDTO);
                        }
                    }
                }
            }
        }
        return deviceDTOList;
    }

    /**
     * 3/8 17:44 已更换
     */
    public List<Integer> getOnlineStatusByList(List<AndroidMonitorTip> list) {
        List<Integer> statusList = new ArrayList<>();
        int online = 0;
        int offline = 0;
        int attention = 0;//是否关注
        int activation = 0;//是否激活
        for (AndroidMonitorTip monitorTips : list) {
            if (monitorTips.isActivation()) {
                if (monitorTips.isOnline()) {
                    online++;
                } else {
                    offline++;
                }
                if (monitorTips.isAttention()) {
                    attention++;
                }
            } else {
                activation++;
            }
        }
        statusList.add(0, list.size());
        statusList.add(1, online);
        statusList.add(2, offline);
        statusList.add(3, attention);
        statusList.add(4, activation);
        return statusList;
    }


    /**
     * 3/8 17:44 已更换
     */
    public List<AndroidMonitorTip> getAndroidMonitorTipsByAlarmType(List<AndroidMonitorTip> androidMonitorTips) {
        List<AndroidMonitorTip> alarmList = new ArrayList<>();
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTips) {
            if (androidMonitorTip.isActivation() && UserSetting.ALARMTYPE != androidMonitorTip.getAlarm_type_id()) {//保证是激活  且报警
                alarmList.add(androidMonitorTip);
            }
        }
        return alarmList;
    }

    /**
     * Android获取历史路径
     * 4/27 9:08
     * 5/15 17:30
     */
    public Result getHistorysByNumAndTimeAndroid(Map<String, String> map) {
//        List<MonitorTips> monitorTipsList = JedisUtil6491.getMonitorTipsAll(8);
//        List<AndroidMonitorTip> monitorTipsList = influxdbDao.queryAndroidMonitorTipsByTime(map);//不在查influxdb 库
        Result result = new Result();
        Map<String, List<Route>> res = new HashMap<>();
        List<Route> routeList = new ArrayList<>();
        String startTime = map.get("begin_time");
        String endTime = map.get("end_time");
//        long st = TimeUtils.StrToDate(startTime).getTime();
//        long et = TimeUtils.StrToDate(endTime).getTime();
        String limit = map.get("limit");
        String dev_number = map.get("imei");
        //[30.082525,103.85656, 30.098705,104.34326, 30.114883,104.82997, 30.131063,105.31667, 30.147243,105.803375, 30.163422,106.290085, 30.179602,106.77679, 30.195782,107.26349, 30.21196,107.75019, 30.22814,108.2369, 30.24432,108.7236, 30.260498,109.210304, 30.276678,109.697014, 30.292858,110.183716, 30.309036,110.67042, 30.325216,111.15712, 30.341396,111.64383, 30.357574,112.13053, 30.373755,112.61723, 30.389935,113.10394, 30.406113,113.590645, 30.422293,114.07735, 30.438473,114.56406, 30.45465,115.05076, 30.47083,115.53746, 30.487011,116.02417, 30.50319,116.51087, 30.51937,116.997574, 30.53555,117.484276, 30.551727,117.970985, 30.567907,118.45769, 30.567907,118.45769, 30.567907,118.45769]
        List<String> lnglatList = JedisUtil6800.getAndroidMonitorTipsLngLatArrayLisByDeviceId(dev_number);
        List<String> timeList = JedisUtil6800.getAndroidMonitorTipsLocationTimeListByDeviceId(dev_number);

        if (lnglatList == null) {
            result.setData(null);
            result.setErrcode(1);
            result.setSuccess(false);
            result.setMsg("设备未找到");
        } else {

            for (int i = 0; i < timeList.size(); i++) {
                if (Long.valueOf(TimeUtils.dateTomillis(timeList.get(i))) < Long.valueOf(startTime) || Long.valueOf(TimeUtils.dateTomillis(timeList.get(i))) > Long.valueOf(endTime)) {
                    timeList.remove(i);
                    lnglatList.remove(i);
                    i--;
                }
            }

            int j = (lnglatList.size() > Integer.valueOf(limit)) ? 1000 : lnglatList.size();
            System.out.println("共有轨迹记录 ：" + lnglatList.size() + " 条");
            for (int i = 0; i < j; i++) {
                Route route = new Route();
                String lat = lnglatList.get(i).split(",")[0];
                String lng = lnglatList.get(i).split(",")[1];
                String time = timeList.get(i).split(",")[0];
                String date = TimeUtils.dateTomillis(time);
                route.setSysTime(date);
                route.setGps_time(date);
                //TODO  定位时间需要从库中 取回      (时间升序)
                route.setLng(lng);
                route.setLat(lat);
                route.setCourse(Double.valueOf(random.nextInt(360) + ""));
                route.setSpeed(random.nextInt(200) + "");
//                route.setSysTime(TimeUtils.getPreTimeByHour(i, 2));
//                route.setGps_time(TimeUtils.getPreTimeByHour(i, 2));
                route.setSequenceNo(i);
                routeList.add(route);
            }
            res.put("pos", routeList);
            result.setMsg("OK");
            result.setErrcode(0);
            result.setSuccess(true);
            result.setData(res);
        }
        return result;
    }


}
