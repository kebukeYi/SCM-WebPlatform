package com.car.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.bean.UserOrg;
import com.car.domain.DeviceLocationInfo;
import com.car.domain.DeviceLocationInfos;
import com.car.domain.StopDeviceDetail;
import com.car.domain.StopDeviceDetails;
import com.car.domain.main.WiredOffline;
import com.car.redis.JedisUtil6800;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:52
 * @Description
 */
@Service
public class ReportService {

    @Autowired
    UserService userService;

    @Autowired
    CommonService commonService;

    static Random random = new Random();

    /**
     * /Report/ShowWirelessOnline
     * 无线上线
     */
    public WiredOffline getShowWirelessOnline(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        System.out.println(userOrg);
        WiredOffline wiredOffline = new WiredOffline();
        System.out.println(userOrg.getWirelessOnline());
        List<Integer> wiredOfflineList = JSONArray.parseArray(userOrg.getWirelessOnline(), Integer.class);
        int result = 0;
        for (Object num : wiredOfflineList) {
            result += (Integer) num;
        }
        wiredOffline.setData(wiredOfflineList);
        wiredOffline.setTotal(result);
        return wiredOffline;
    }

    /**
     * /Report/ShowWiredOffline
     * 有线离线
     */
    public WiredOffline getShowWiredOffline(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        System.out.println(userOrg);
        WiredOffline wiredOffline = new WiredOffline();
        System.out.println(userOrg.getWiredOffline());
        List<Integer> wiredOfflineList = JSONArray.parseArray(userOrg.getWiredOffline(), Integer.class);
        int result = 0;
        for (Object num : wiredOfflineList) {
            result += (Integer) num;
        }
        wiredOffline.setData(wiredOfflineList);
        wiredOffline.setTotal(result);
        return wiredOffline;
    }

    /**
     * /Report/ShowWirelessOffline
     * 无线离线
     */
    public WiredOffline getShowWirelessOffline(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        System.out.println(userOrg);
        WiredOffline wiredOffline = new WiredOffline();
        System.out.println(userOrg.getWirelessOffline());
        List<Integer> wiredOfflineList = JSONArray.parseArray(userOrg.getWirelessOffline(), Integer.class);
        int result = 0;
        for (Object num : wiredOfflineList) {
            result += (Integer) num;
        }
        wiredOffline.setData(wiredOfflineList);
        wiredOffline.setTotal(result);
        return wiredOffline;
    }


    /**
     * @return com.car.domain.DeviceLocationInfos
     * @author mmy
     * @date 2020/2/18  21:26
     * @Description Report/ShowDeviceLocationInfo
     * limit: 15
     * start: 0             已更改
     * page: 1
     * WithChildren: "true"
     * OrgId: "1a0c434c-4386-414d-ae29-a82700fac16b"
     * ModeId: "所有型号"  1 2 3 4 5 6 7 8  9
     * ModelType: "设备类型"   有线: 0   无线：1   无线不充电：10  无线可充电：11
     * Key: "181219310018261"
     * TerminalMonitor.html
     */
    public DeviceLocationInfos getDeviceLocationInfoByMap(Map<String, Object> map) {
        List<DeviceLocationInfo> deviceLocationInfoList = new ArrayList<>();
        DeviceLocationInfos deviceLocationInfos = new DeviceLocationInfos();
        List<AndroidMonitorTip> androidMonitorTipList = null;
        String userId = (String) map.get("userId");//1-100
        String orgOrCataId = (String) map.get("OrgId");// G101    C1    U1
        String ModeId = (String) map.get("ModeId");//  一系列型号 : C18-TRV 对应是 2 ，等等 ...
        String ModelName = getModelNameByModelId(ModeId);
        String ModelType = (String) map.get("ModelType");
        String ModelTypeName = getModelTypeNameByModeType(ModelType);//  ModelType: "设备类型"   有线: 0   无线：1   无线不充电：10  无线可充电：11
        String Key = (String) map.get("Key");//设备号
        if (Key != null && Key.length() > 0) {
            System.out.println("进入搜索功能");
            AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(Key);
            DeviceLocationInfo deviceLocationInfo = new DeviceLocationInfo();
            if (androidMonitorTip != null) {
                BeanUtils.copyProperties(androidMonitorTip, deviceLocationInfo);
                deviceLocationInfo.setLocation_type(getLocationType(androidMonitorTip.getLocation_type()));
                deviceLocationInfo.setModel_name(getModelNameByModelId(androidMonitorTip.getModel_id()));
                deviceLocationInfo.setModel_type_text(getModelTypeNameByModeType(androidMonitorTip.getModel_type()));
                deviceLocationInfoList.add(deviceLocationInfo);
                deviceLocationInfos.setTotal(deviceLocationInfoList.size());
                deviceLocationInfos.setData(deviceLocationInfoList);
                return deviceLocationInfos;
            }
            return null;
        }
        androidMonitorTipList = userService.getAndroidMonitorTipsByUserIdOrOrgIdOrCataId(map);
        System.out.println("用户" + userId + "下所有设备大小：" + androidMonitorTipList.size());
        androidMonitorTipList = subListByModelIdAndModelType(map, androidMonitorTipList);
        System.out.println("用户" + userId + "下所有 " + ModeId + " - " + ModelType + " 设备大小：" + androidMonitorTipList.size());
        int length = androidMonitorTipList.size();//筛选后的长度
        int start = 0;
        int page = 1;
        int limit = Integer.parseInt(map.get("limit") + "");
        start = Integer.parseInt(map.get("start") + "");
        page = Integer.parseInt(map.get("page") + "");
        int end = page * limit;
//        end = end > length ? length - 1 : end;
        end = end > length || end == length ? length - 1 : end;
//todo
        for (int i = start; i <= end; i++) {
            DeviceLocationInfo deviceLocationInfo = new DeviceLocationInfo();
            AndroidMonitorTip androidMonitorTip = androidMonitorTipList.get(i);
            BeanUtils.copyProperties(androidMonitorTip, deviceLocationInfo);
            deviceLocationInfo.setLocation_type(getLocationType(androidMonitorTip.getLocation_type()));
            deviceLocationInfo.setModel_name(getModelNameByModelId(androidMonitorTip.getModel_id()));
            deviceLocationInfo.setModel_type_text(getModelTypeNameByModeType(androidMonitorTip.getModel_type()));
            deviceLocationInfoList.add(deviceLocationInfo);
        }
        deviceLocationInfos.setTotal(androidMonitorTipList.size());
        deviceLocationInfos.setData(deviceLocationInfoList);
        deviceLocationInfos.setLimit(limit);
        deviceLocationInfos.setPage(page);
        deviceLocationInfos.setExcel(false);
        return deviceLocationInfos;
    }

    /**
     * 已更改
     */
    public List<AndroidMonitorTip> subListByModelIdAndModelType(Map<String, Object> map, List<AndroidMonitorTip> monitorTipsList) {
        List<AndroidMonitorTip> list = new ArrayList<>();
        String ModeId = (String) map.get("ModeId");//  一系列型号 : C18-TRV 对应是 2 ，等等 ...
        String ModelType = (String) map.get("ModelType");// 0 1 11 10
        if ("所有型号".equals(ModeId) || ModeId == null) {
            if ("设备类型".equals(ModelType) || ModelType == null) {
                return monitorTipsList;//需要全部
            } else {
                for (AndroidMonitorTip monitorTips : monitorTipsList) {
                    if (monitorTips.getModel_type().equals(ModelType)) {
                        list.add(monitorTips);
                    }
                }
                return list;
            }
        } else {
            if ("设备类型".equals(ModelType) || ModelType == null) {
                for (AndroidMonitorTip monitorTips : monitorTipsList) {
                    if (monitorTips.getModel_id().equals(ModeId)) {
                        list.add(monitorTips);
                    }
                }
                return list;
            } else {
                for (AndroidMonitorTip monitorTips : monitorTipsList) {
                    if (monitorTips.getModel_id().equals(ModeId)) {
                        if (monitorTips.getModel_type().equals(ModelType)) {
                            list.add(monitorTips);
                        }
                    }
                }
                return list;
            }
        }
    }


    /**
     * Interval: "2880"   "1440" 默认搜按最近的停留    "-1" 自定义区间
     * MinInterval: "0"
     * MaxInterval: "4"
     * DeviceNumber: "312409444442954"
     * ShowStopListByDeviceNumber
     */
    public StopDeviceDetails ShowStopListByDeviceNumber(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        String MinInterval = (String) map.get("MinInterval");
        String MaxInterval = (String) map.get("MaxInterval");
        String DeviceNumber = (String) map.get("DeviceNumber");
        String temp = (String) map.get("Interval");
        int Interval = 0;
        if (temp != null) {
            Interval = Integer.parseInt((String) map.get("Interval"));
        }
        if (Interval > 0) {// 不是自定义时间
            int day = Interval / 1440;
        } else {//自定义时间

        }
        List<StopDeviceDetail> stopDeviceDetailList = new ArrayList<>();
        stopDeviceDetailList = commonService.getStopDeviceDetailsByDevId(DeviceNumber);
        //todo 在做处理


        int length = stopDeviceDetailList.size();//筛选后的长度
        int start = 0;
        int page = 1;
        int limit = Integer.parseInt("" + map.get("limit"));
        start = Integer.parseInt("" + map.get("start"));
        page = Integer.parseInt("" + map.get("page"));
        int end = page * limit;
//        end = end > length ? length - 1 : end;
        end = end > length || end == length ? length - 1 : end;
        stopDeviceDetailList = stopDeviceDetailList.subList(start, end + 1);
        StopDeviceDetails stopDeviceDetails = new StopDeviceDetails();
        stopDeviceDetails.setData(stopDeviceDetailList);
        stopDeviceDetails.setExcel(false);
        stopDeviceDetails.setLimit(limit);
        stopDeviceDetails.setTotal(stopDeviceDetailList.size());
        return stopDeviceDetails;
    }


    public static String getModelTypeNameByModeType(String ModelTypeId) {
        String ModelType = "";
        if (ModelTypeId == null || "设备类型".equals(ModelTypeId)) {
            return "";
        }
        switch (ModelTypeId) {
            case "0":
                ModelType = "有线";
                break;
            case "1":
                ModelType = "无线";
                break;
            case "10":
                ModelType = "无线不充电";
                break;
            case "11":
                ModelType = "无线可充电";
                break;
            default:
                break;
        }
        return ModelType;
    }

    public static String getModelNameByModelId(String ModelId) {
        String ModelName = "";
        if (ModelId == null || "所有型号".equals(ModelId)) {
            return ModelName;
        }
        switch (ModelId) {
            case "1":
                ModelName = "R01";
                break;
            case "2":
                ModelName = "T16";
                break;
            case "3":
                ModelName = "R06";
                break;
            case "4":
                ModelName = "W30";
                break;
            case "5":
                ModelName = "C12";
                break;
            case "6":
                ModelName = "S01";
                break;
            case "7":
                ModelName = "X11";
                break;
            case "8":
                ModelName = "C06";
                break;
            case "9":
                ModelName = "C56";
                break;
            case "10":
                ModelName = "C09";
                break;
            case "11":
                ModelName = "S12";
                break;
            case "12":
                ModelName = "JT808有线";
                break;
            case "13":
                ModelName = "S09";
                break;
            case "14":
                ModelName = "C14";
                break;
            case "15":
                ModelName = "C13";
                break;
            case "16":
                ModelName = "C16";
                break;
            case "17":
                ModelName = "W36";
                break;
            case "18":
                ModelName = "JT808无线";
                break;
            case "19":
                ModelName = "S06";
                break;
            case "20":
                ModelName = "S6";
                break;
            case "21":
                ModelName = "无线部标测试";
                break;
            case "22":
                ModelName = "C12-JT";
                break;
            case "23":
                ModelName = "C11-JT";
                break;
            case "24":
                ModelName = "C06-TRV";
                break;
            case "25":
                ModelName = "S11";
                break;
            case "26":
                ModelName = "C17B";
                break;
            case "27":
                ModelName = "D01";
                break;
            case "28":
                ModelName = "D05";
                break;
            case "29":
                ModelName = "W38";
                break;
            case "30":
                ModelName = "C18-JT";
                break;
            case "31":
                ModelName = "C18-TRV";
                break;
            case "32":
                ModelName = "S11-JT";
                break;
            case "33":
                ModelName = "S01-JT";
                break;
            case "34":
                ModelName = "S06-jt测试";
                break;
            case "35":
                ModelName = "C18A-JT";
                break;
            case "36":
                ModelName = "C11-JT-NEW";
                break;
            case "37":
                ModelName = "S06-NEW";
                break;
            case "38":
                ModelName = "X5";
                break;
            case "39":
                ModelName = "C16";
                break;
            case "40":
                ModelName = "APPCS01";
                break;
            case "41":
                ModelName = "APPCS02";
                break;
            case "42":
                ModelName = "SG2000-G01E";
                break;
            case "43":
                ModelName = "C11-JT-4P";
                break;
            case "44":
                ModelName = "W36-JT";
                break;
            default:
                break;
        }
        return ModelName;
    }

    /**
     * 根据报警类型 获取对应名称
     */
    public static String getAlarmNameByAlarmType(String AlarmType) {
        String alarmName = "";
        switch (AlarmType) {
            case "1":
                alarmName = "振动报警 ";
                break;
            case "2":
                alarmName = "关机  ";
                break;
            case "3":
                alarmName = "电池低 ";
                break;
            case "4":
                alarmName = "sos  ";
                break;
            case "5":
                alarmName = "超速  ";
                break;
            case "6":
                alarmName = "离线 ";
                break;
            case "8":
                alarmName = "超出栅栏 ";
                break;
            case "9":
                alarmName = "移动  ";
                break;
            case "24":
                alarmName = "进入栅栏 ";
                break;
            case "31":
                alarmName = "超出区域 ";
                break;
            case "32":
                alarmName = "拆开 ";
                break;
            case "33":
                alarmName = "灯光 ";
                break;
            case "34":
                alarmName = "使用 ";
                break;
            case "36":
                alarmName = "蓝牙 ";
                break;
            case "40":
                alarmName = "进入危险区域 ";
                break;
            case "41":
                alarmName = "离开危险区域 ";
                break;
            case "42":
                alarmName = "长时间危险区域 ";
                break;
            case "44":
                alarmName = "相撞 ";
                break;
            case "45":
                alarmName = "加速 ";
                break;
            case "46":
                alarmName = "减速 ";
                break;
            case "47":
                alarmName = "翻倒 ";
                break;
            case "48":
                alarmName = "急转弯 ";
                break;
            case "49":
                alarmName = "下拉电阻 ";
                break;
            default:
                break;
        }
        return alarmName;
    }

    public static String getLocationType(String temp) {
        String LocationType = "基站";
        switch (temp) {
            case "0":
                LocationType = "基站";
                break;
            case "1":
                LocationType = "GPS";
                break;
            case "2":
                LocationType = "北斗";
                break;
            case "3":
                LocationType = "北斗+GPS";
                break;
            default:
                break;
        }
        return LocationType;
    }


    /**
     * 弃用
     */
    public static String getDeviceType(String temp) {
        String LocationType = "在线";
        switch (temp) {
            case "0":
                LocationType = "在线";
                break;
            case "1":
                LocationType = "停留";
                break;
            case "2":
                LocationType = "离线";
                break;
            case "3":
                LocationType = "失效";
                break;
            default:
                break;
        }
        return LocationType;
    }

    /**
     * 普通乘用车、活顶乘用车、高级乘用车、小型乘用车、敞篷车、舱背乘用车、旅行车、多用途乘用车、短头乘用车、越野乘用车、专用乘用车
     */
    public static String getCarCarTypeNameByCarModel(String CarType) {
        String CarModelName = "小型车";
        switch (CarType) {
            case "0":
                CarModelName = "多用途车";
                break;
            case "1":
                CarModelName = "轿车";
                break;
            case "2":
                CarModelName = "商务车";
                break;
            case "3":
                CarModelName = "大巴";
                break;
            case "4":
                CarModelName = "小巴";
                break;
            case "5":
                CarModelName = "大客车";
                break;
            case "6":
                CarModelName = "大货车";
                break;
            case "7":
                CarModelName = "小货车";
                break;
            case "8":
                CarModelName = "农用车";
                break;
            case "9":
                CarModelName = "工程车";
                break;
            case "10":
                CarModelName = "专用乘用车";
                break;
            default:
                break;
        }
        return CarModelName;
    }

    //得到车的品牌
    public static String getCarBrandById(String BrandId) {
        String Brand = "";
        switch (BrandId) {
            case "0":
                Brand = "解放";
                break;
            case "1":
                Brand = "上汽";
                break;
            case "2":
                Brand = "三菱";
                break;
            case "3":
                Brand = "";
                break;
            case "4":
                Brand = "";
                break;
            default:
                break;
        }
        return Brand;
    }

    //得到车的颜色
    public static String getCarColorById(String CarColorId) {
        String CarColor = "";
        switch (CarColorId) {
            case "0":
                CarColor = "黑色";
                break;
            case "1":
                CarColor = "白色";
                break;
            case "2":
                CarColor = "红色";
                break;
            case "3":
                CarColor = "灰色";
                break;
            case "4":
                CarColor = "银色";
                break;
            case "5":
                CarColor = "黄色";
                break;
            default:
                break;
        }
        return CarColor;
    }

    //得到车的型号
    public static String getCarModelById(String ModelId) {
        String Model = "";
        switch (ModelId) {
            case "0":
                Model = "中型";
                break;
            case "1":
                Model = "重型";
                break;
            case "2":
                Model = "轻型";
                break;
            default:
                break;
        }
        return Model;
    }

}
