package com.car.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.bean.Catalog;
import com.car.bean.Organization;
import com.car.bean.UserOrg;
import com.car.domain.DeviceDetail;
import com.car.domain.Fence;
import com.car.domain.Online;
import com.car.domain.main.DailyAlarm;
import com.car.domain.main.Distribution;
import com.car.domain.main.IncrementModel;
import com.car.mysql.impl.CarManagementImpl;
import com.car.mysql.impl.Terminalmpl;
import com.car.redis.JedisUtil6800;
import com.car.setting.UserSetting;
import com.car.utils.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:00
 * @Description
 */
@Service
public class DeviceService {


    @Autowired
    CommonService commonService;

    @Autowired
    CarManagementImpl carManagement;

    @Autowired
    Terminalmpl terminalmpl;


    /**
     * 终端绑定车辆
     * 终端设置新的车牌号
     * 车辆设置新的终端号
     * CarId: "4765"
     * TerIds: "618613949812014"
     */
    @Transactional(rollbackFor = Exception.class)
    public void AssociateVehicle(Map<String, Object> map) {
        System.out.println("/api/Device/AssociateVehicle " + map);
        carManagement.AssociateVehicle(map);
        terminalmpl.AssociateVehicle(map);
    }

    /**
     * /api/Device/DisassociateVehicle
     * 终端取消绑定车辆
     * 在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,
     * 可以让事物在遇到非运行时异常时也回滚
     */
    @Transactional(rollbackFor = Exception.class)
    public void DisassociateVehicle(JSONArray jsonArray) {
        System.out.println("/api/Device/DisassociateVehicle " + jsonArray);
        HashMap<String, Object> map = new HashMap<>();
        for (Object id : jsonArray) {
            map.put("TerIds", id);
            terminalmpl.DisassociateVehicle(map);
            carManagement.DisassociateVehicle(map);
        }
        //  D-1257615            157618093014728  ->162203372877781
    }

    /**
     * 删除终端设备
     * api/Device/Remove
     * 0: "162203372877781"
     */
    @Transactional(rollbackFor = Exception.class)
    public void Remove(JSONArray jsonArray) {
        System.out.println("api/Device/Remove " + jsonArray);
        for (Object id : jsonArray) {
            terminalmpl.Remove(jsonArray);

        }
    }


    /**
     * device/GetDailyAlarm
     */
    public List<DailyAlarm> GetDailyAlarm(Map<String, Object> map) {
        String userId = (String) map.get("userId");//1-100
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        List<DailyAlarm> alarmList = new ArrayList<>();
        alarmList = JSONObject.parseArray(userOrg.getDailyAlarm(), DailyAlarm.class);
        System.out.println("device/GetDailyAlarm " + alarmList);
        return alarmList;
    }

    /**
     * device/GetIncrementModel
     */
    public List<IncrementModel> GetDevIncrementModel(Map<String, Object> map) {
        String userId = (String) map.get("userId");//1-100
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        List<IncrementModel> IncrementModel = new ArrayList<>();
        IncrementModel = JSONArray.parseArray(userOrg.getDevIncrementModel(), IncrementModel.class);
        return IncrementModel;
    }


    /**
     * GetExpireDate
     */
    public List<IncrementModel> GetExpireDate(Map<String, Object> map) {
        String userId = (String) map.get("userId");//1-100
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        List<IncrementModel> IncrementModel = new ArrayList<>();
        IncrementModel = JSONArray.parseArray(userOrg.getExpireDateModel(), IncrementModel.class);
        return IncrementModel;
    }


    /**
     * Device/ShowDeviceDetails   已更改
     */
    public DeviceDetail getDevicelById(String devId) {
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(devId);
        DeviceDetail deviceDetail = new DeviceDetail();
        if (androidMonitorTip != null) {
            System.out.println(androidMonitorTip.getExpire_date());
            BeanUtils.copyProperties(androidMonitorTip, deviceDetail);
            System.out.println(deviceDetail.getExpire_date());
            deviceDetail.setModel_name(ReportService.getModelNameByModelId(androidMonitorTip.getModel_id()));
            deviceDetail.setModel_type_text(ReportService.getModelTypeNameByModeType(androidMonitorTip.getModel_type()));
        }
        return deviceDetail;
    }

    /**
     * Device/ShowFenceDetails  已更改
     */
    public Fence getFenceDetailsById(Map<String, String> map) {
        String fenceId = map.get("deviceNumber");
        String fenceStr = JedisUtil6800.getString8(fenceId);
        Fence fence = null;
        String userName = "";
        String orgName = "";
        String cataName = "";
        if (fenceId != null) {
            fence = JSONObject.parseObject(fenceStr, Fence.class);
            String userId = fence.getUserId();
            UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);//用户是都有的
            userName = userOrg.getUserName();
            JSONArray orgArray = JSONArray.parseArray(fence.getOrganizationIds());//不一定有
            if (orgArray != null && orgArray.size() > 0) {
                for (Object obj : orgArray) {
                    Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(obj + ""), Organization.class);
                    orgName = orgName + organization.getOrganization_name() + " , ";
                }
                orgName = orgName.substring(0, orgName.length() - 2);
                JSONArray cataArray = JSONArray.parseArray(fence.getCatalogueIds());
                if (cataArray != null && cataArray.size() > 0) {//不一定有
                    for (Object obj : cataArray) {
                        Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(obj + ""), Catalog.class);
                        cataName = cataName + catalog.getCatalog_name() + " , ";
                    }
                    cataName = cataName.substring(0, cataName.length() - 2);
                }
            }
            fence.setUserName(userName);
            fence.setOrganizationIdsName(orgName);
            fence.setCatalogueIdsName(cataName);
            JedisUtil6800.setString8(fenceId, JSON.toJSONString(fence));
        }
        return fence;
    }


    /**
     * Device/OnlineDetail   已更改
     */
    public List<Online> getOnlineDetailListByNum(Map<String, String> map) {
        String DeviceNumber = map.get("DeviceNumber");
        String Start = map.get("Start");
        String End = map.get("End");
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(DeviceNumber);
        List<Integer> onlineList = JSONObject.parseArray(androidMonitorTip.getOnline_list(), Integer.class);
        List<Online> onlines = new ArrayList<>();
        int length = onlineList.size();
        Collections.reverse(onlineList);//反转list
        for (int i = 0; i < length; i++) {
            int flag = onlineList.get(i);
            Online online = new Online();
            if (flag == 1) {
                online.setIsOnline(true);
            } else {
                online.setIsOnline(false);
            }
            online.setOnlineDate(TimeUtils.getPreTimeByDay(-i, 1));
            onlines.add(online);
        }
        return onlines;
    }


    /**
     * api/device/GetDistribution  已更改
     * 每几个小时 把数据 更新在用户 Bean 中
     */
    public List<Distribution> getDistributionByLocations(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        List<Distribution> distributions = JSON.parseArray(userOrg.getDistributions(), Distribution.class);
        return distributions;
    }

    /**
     * 获取各省份数量
     */
    public static Map<Integer, Integer> getProvinceSunByDistributionList(List<Distribution> distributionList) {
        /*分组， 按照字段中某个属性将list分组*/
        Map<Integer, List<Distribution>> map = distributionList.stream().collect(Collectors.groupingBy(t -> t.getAdCode()));
        Map<Integer, Integer> integerMap = new HashMap<>();
//        System.out.println("获取各省份数量分组" + map);
        /*然后再对map处理，这样就方便取出自己要的数据*/
        for (Map.Entry<Integer, List<Distribution>> entry : map.entrySet()) {
//            System.out.println("key:" + entry.getKey());
//            System.out.println("value:" + entry.getValue().size());
            integerMap.put(entry.getKey(), entry.getValue().size());
        }
        return integerMap;
    }

    /**
     * 根据baiduAPI省会名字 返回AdCode
     */
    public static int getAdCodeByProvinceName(String province) {
        int AdCode = 13;
        switch (province) {
            case "北京市":
                AdCode = 11;
                break;
            case "天津市":
                AdCode = 12;
                break;
            case "河北省":
                AdCode = 13;
                break;
            case "山西省":
                AdCode = 14;
                break;
            case "内蒙古自治区":
                AdCode = 15;
                break;
            case "辽宁省":
                AdCode = 21;
                break;
            case "吉林省":
                AdCode = 22;
                break;
            case "黑龙江省":
                AdCode = 23;
                break;
            case "上海市":
                AdCode = 31;
                break;
            case "江苏省":
                AdCode = 32;
                break;
            case "浙江省":
                AdCode = 33;
                break;
            case "安徽省":
                AdCode = 34;
                break;
            case "福建省":
                AdCode = 35;
                break;
            case "江西省":
                AdCode = 36;
                break;
            case "山东省":
                AdCode = 37;
                break;
            case "河南省":
                AdCode = 41;
                break;
            case "湖北省":
                AdCode = 42;
                break;
            case "湖南省":
                AdCode = 43;
                break;
            case "广东省":
                AdCode = 44;
                break;
            case "广西壮族自治区":
                AdCode = 45;
                break;
            case "海南省":
                AdCode = 46;
                break;
            case "重庆市":
                AdCode = 50;
                break;
            case "四川省":
                AdCode = 51;
                break;
            case "贵州省":
                AdCode = 52;
                break;
            case "云南省":
                AdCode = 53;
                break;
            case "西藏自治区":
                AdCode = 54;
                break;
            case "陕西省":
                AdCode = 61;
                break;
            case "甘肃省":
                AdCode = 62;
                break;
            case "青海省":
                AdCode = 63;
                break;
            case "宁夏回族自治区":
                AdCode = 64;
                break;
            case "新疆维吾尔自治区":
                AdCode = 65;
                break;
            case "台湾省":
                AdCode = 71;
                break;
            case "香港":
                AdCode = 81;
                break;
            case "澳门":
                AdCode = 82;
                break;
            default:
                break;
        }
        return AdCode;
    }


}
