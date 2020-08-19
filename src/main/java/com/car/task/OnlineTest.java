package com.car.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.baiduMapRoute.HttpRoute;
import com.car.bean.Catalog;
import com.car.bean.Organization;
import com.car.bean.UserOrg;
import com.car.domain.Fence;
import com.car.redis.JedisUtil6800;
import com.car.service.FenceService;
import com.car.utils.KeyUtil;
import com.car.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/20  8:36
 * @Description
 */
public class OnlineTest {

    public static void main(String[] args) {
        updataUser();
    }

    /*
    开始新建用户  新建组  新建目录 新建设备   测试
     */
    public static void creatNewUser(String[] args) {
        String devId = "161117090700009";
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9("1"), UserOrg.class);//先复制一个普通用户
        List<String> organizations = new ArrayList<>();
        List<String> catalogs = new ArrayList<>();
        List<String> fences = new ArrayList<>();

        List<String> bind_time = new ArrayList<>();//设备级别 的绑定时间
        bind_time.add(TimeUtils.getGlnz());
        List<String> fenceType = new ArrayList<>();//设备级别 的围栏类型
        fenceType.add("1");//进围栏
        List<String> androidMonitorTipList = new ArrayList<>();
        androidMonitorTipList.add(devId);//设备号

        //用户 9库
        List<String> statInfo = new ArrayList<>();//统计用户在线线数量list
        List<String> distributions = new ArrayList<>();//统计用户设备所在地理位置list
        String userId = KeyUtil.genUniqueKey();
        userOrg.setCreateTime(TimeUtils.getGlnz());
        userOrg.setPassword("123456");
        userOrg.setParentId("admin");
        userOrg.setParentName("admin");
        userOrg.setUserName("唐山朴能环保科技有限公司");
        userOrg.setUser_id(userId);

        String orgId = KeyUtil.genUniqueKey();
        String cataId = KeyUtil.genUniqueKey();
        String fenceId = KeyUtil.genUniqueKey();

        organizations.add(orgId);
        catalogs.add(cataId);
        fences.add(fenceId);

        userOrg.setStatInfo(JSON.toJSONString(statInfo));
        userOrg.setDistributions(JSON.toJSONString(distributions));
        userOrg.setOrgnizationIds(JSON.toJSONString(organizations));
        userOrg.setCatalogueIds(JSON.toJSONString(catalogs));
        userOrg.setFence_ids(JSON.toJSONString(fences));
        System.out.println(userOrg);
        JedisUtil6800.setString9(userOrg.getUser_id(), JSON.toJSONString(userOrg));

        //组 6库
        Organization organization = JSONObject.parseObject(JedisUtil6800.getString6("1583500312155511924"), Organization.class);// 先方便复制一个其他组
        organization.setOrganization_id(orgId);
        organization.setOrganization_name("唐山市");
        organization.setCreat_time(TimeUtils.getGlnz());
        List<String> group_bind_time = new ArrayList<>();//3级联动  针对于 “组”级别关联
        List<String> group_fence_alarm_type = new ArrayList<>();
        List<String> group_fence_ids = new ArrayList<>();

        organization.setCatalogue_ids(JSON.toJSONString(catalogs));
        organization.setParent_id(userId);//父级别
        organization.setFence_ids(JSON.toJSONString(fences));//设备级别的 关联
        organization.setGroup_fence_alarm_type(JSON.toJSONString(group_fence_alarm_type));//组级别的 关联
        organization.setGroup_fence_ids(JSON.toJSONString(group_fence_ids));
        organization.setGroup_bind_time(JSON.toJSONString(group_bind_time));
        System.out.println(organization);
        JedisUtil6800.setString6(orgId, JSON.toJSONString(organization));

        //目录 7库
        Catalog catalog = new Catalog();//直接新建吧
        catalog.setParent_id(orgId);
        catalog.setCatalog_id(cataId);
        catalog.setCatalog_name("朴能环保科技有限公司");
        catalog.setCreat_time(TimeUtils.getGlnz());

        catalog.setDevice_ids(JSON.toJSONString(androidMonitorTipList));//加入设备号
        catalog.setFence_ids(JSON.toJSONString(fences));//设备级别的 关联

        catalog.setGroup_bind_time(JSON.toJSONString(group_bind_time));//组级别的 关联
        catalog.setGroup_fence_ids(JSON.toJSONString(group_fence_ids));
        catalog.setGroup_fence_alarm_type(JSON.toJSONString(group_fence_alarm_type));
        System.out.println(catalog);
        JedisUtil6800.setString7(cataId, JSON.toJSONString(catalog));


        //围栏8 库  数据 118.18503856658936|39.69396263878166,4690.0
        Fence fence = new Fence();
        fence.setId(fenceId);
        fence.setCatalogueIds(JSON.toJSONString(catalogs));//关联目录ids
        fence.setOrganizationIds(JSON.toJSONString(organizations));//关联组ids
        fence.setUserId(userId);//关联用户ids
        fence.setUserName(userOrg.getUserName());
        fence.setFenceName("朴能环保科技有限公司ABC");
        fence.setCreatTime(TimeUtils.getGlnz());
        fence.setFenceType(1);
        fence.setEnabled(true);
        String lngLat = "118.18503856658936|39.69396263878166";
        fence.setFenceTypeText(FenceService.getFenceNameByFenceType(1 + "", lngLat).get(0));
        fence.setFenceData(FenceService.getFenceNameByFenceType(1 + "", lngLat).get(1));
//        fence.setFenceData("118.18503856658936|39.69396263878166,4690.0");
        System.out.println(fence);
        JedisUtil6800.setString8(fenceId, JSON.toJSONString(fence));

        //设备4库
        String lng = "118.167168";
        String lat = "39.646874";
        List<String> address = HttpRoute.getAddressByLocation(lat, lng);
        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4("100022857985892"), AndroidMonitorTip.class);//属性太多 先复制一个
        androidMonitorTip.setDev_name("S110009");
        androidMonitorTip.setAliase("S110009");
        androidMonitorTip.setDev_number(devId);
        androidMonitorTip.setModel_type("1");//无线
        androidMonitorTip.setModel_id("25");//S11
        androidMonitorTip.setLocation_type("0");//基站
        androidMonitorTip.setActivation(true);//激活
        androidMonitorTip.setOnline(false);//离线
        androidMonitorTip.setAttention(true);//关注
        androidMonitorTip.setDisabled(false);//未过期

        androidMonitorTip.setEfence_support(true);
        androidMonitorTip.setBind_fence(true);
        androidMonitorTip.setStatus("NotDisassembly, Normal");
        androidMonitorTip.setOnline_status("OffLine:0:14:36:40");
        androidMonitorTip.setLocation_time(TimeUtils.getGlnz());
        androidMonitorTip.setLast_update_time("2020-03-19T19:03:46.693");
        androidMonitorTip.setReceive_time("2020-03-19T19:03:46.693");
        androidMonitorTip.setStop_time("");


        androidMonitorTip.setUser_id(userId);
        androidMonitorTip.setGroup_id(orgId);
        androidMonitorTip.setGroup_name(organization.getOrganization_name());
        androidMonitorTip.setCatalogue_id(cataId);
        androidMonitorTip.setCata_name(catalog.getCatalog_name());
        androidMonitorTip.setFence_id(JSON.toJSONString(fences));//一个设备可以关联很多围栏
        androidMonitorTip.setBind_time(JSON.toJSONString(bind_time));
        androidMonitorTip.setFence_alarm_type(JSON.toJSONString(fenceType));

        androidMonitorTip.setAlarm_type_id(0);//先别报警

        androidMonitorTip.setLng(lng);
        androidMonitorTip.setLat(lat);
        androidMonitorTip.setLnglat(lng + "," + lat);

        androidMonitorTip.setAddress(address.get(0));
        androidMonitorTip.setProvince(address.get(1));
        androidMonitorTip.setCity(address.get(2));
        System.out.println(androidMonitorTip);
        JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));

    }


    /*
     * 对同一个用户进行 添加组、目录、设备等
     */
    public static void addDevToUser() {
        // 1584671979279729865 -> 唐山朴能环保科技有限公司
//        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9("1584671979279729865"), UserOrg.class);//可以直接找到上行 新建的用户

        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4("161420110050001"), AndroidMonitorTip.class);
        Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(androidMonitorTip.getCatalogue_id()), Catalog.class);//找到设备所属于的 目录id 直接添加
        List<String> devIds = JSON.parseArray(catalog.getDevice_ids(), String.class);

        List<String> aList = new ArrayList<String>();
        //      161117090700009  从这个设备复制下来的各个新建设备  因此新建设备的组、目录、用户id 都一样，只需要在新建设备的所属目录中  把设备号添加进去 即可展示。
        aList.add("161420110050005");
        aList.add("161420110050003");
        aList.add("161420110050008");
        aList.add("161420110050007");
        aList.add("161420110050002");
        aList.add("161420110050004");
        aList.add("161420110050006");
        aList.add("161420110050010");
        aList.add("161420110050009");
        aList.add("161420110050001");
        devIds.addAll(aList);
        System.out.println("之后设备数量  :  " + devIds.size());
        catalog.setDevice_ids(JSON.toJSONString(devIds));
        JedisUtil6800.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));

    }


    /*
     * 修改 用户、组、目录信息
     */
    public static void updataUser() {
        //张家港市   SCM
        String groupName = "张家港市";
        String cataName = "SCM";
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9("1584671979279729865"), UserOrg.class);//用户信息
        userOrg.setUserName(groupName + cataName);
        System.out.println(userOrg.getUserName());
        JedisUtil6800.setString9(userOrg.getUser_id(), JSON.toJSONString(userOrg));
        JSONArray orgs = JSONArray.parseArray(userOrg.getOrgnizationIds());//用户手下的组
        for (Object obj : orgs) {
            Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(obj + ""), Organization.class);
            organization.setOrganization_name(groupName);
            System.out.println(organization.getOrganization_name());
            JSONArray catas = JSONArray.parseArray(organization.getCatalogue_ids());
            for (Object cataId : catas) {//组下的目录
                Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);
                catalog.setCatalog_name(cataName);
                System.out.println(catalog.getCatalog_name());
                JSONArray devs = JSONArray.parseArray(catalog.getDevice_ids());
                for (Object devId : devs) {//目录下的设备
                    AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(devId + ""), AndroidMonitorTip.class);
                    androidMonitorTip.setGroup_name(groupName);
                    androidMonitorTip.setCata_name(cataName);
                    JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
                }
                JedisUtil6800.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));
            }
            JedisUtil6800.setString6(organization.getOrganization_id(), JSON.toJSONString(organization));
        }
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
    根据报警类型 获取对应名称
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


}
