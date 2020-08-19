package com.car.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.baiduMapRoute.HttpRoute;
import com.car.bean.Catalog;
import com.car.bean.Organization;
import com.car.bean.UserOrg;
import com.car.domain.*;
import com.car.domain.main.DailyAlarm;
import com.car.domain.main.Distribution;
import com.car.domain.main.IncrementModel;
import com.car.influxdb.influxdbDao;
import com.car.service.CommonService;
import com.car.service.DeviceService;
import com.car.service.MonitorService;
import com.car.service.ReportService;
import com.car.setting.UserSetting;
import com.car.utils.KeyUtil;
import com.car.utils.LocationUtils;
import com.car.utils.ReadJSONFile;
import com.car.utils.TimeUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/1/20  8:53
 * @Description 模拟数据类
 */
public class simulationData {

    /**
     * 该方法的作用是生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n。
     */
    public static List<MonitorTips> monitorTipsList = new ArrayList<>();
    public static List<MonitorTips> userMonitorTipsList = new ArrayList<>();
    public static List<UserOrg> userOrgList = new ArrayList<>();
    public static Random randomLat = new Random(10);
    public static Random randomLng = new Random(20);
    public static Random random = new Random(100);


    /**
     * 创造数据4 Andorid Bean库
     */
    public static void main75(String[] args) {
        List<MonitorTips> monitorTipsAll = JedisUtil6800.getMonitorTipsAll(5);
        String[] alarmType = new String[]{"1", "2", "3", "4", "5", "6", "8", "9", "24", "31", "32", "33", "34", "36", "40", "41", "42", "44", "45", "46", "47", "48", "49"};
        String[] devType = new String[]{"GT06S", "GM03C", "GS03B", "GM02A+"};
        String[] number = new String[]{"冀B6W8", "", "", "", "", ""};
        for (MonitorTips monitorTips : monitorTipsAll) {
            String orgId = monitorTips.getOrgnizationId();
            String cataId = monitorTips.getCatalogueId();
            AndroidMonitorTip androidMonitorTip = new AndroidMonitorTip();

            androidMonitorTip.setImei(monitorTips.getDeviceNumber());
            androidMonitorTip.setIccid(monitorTips.getICCID());
            androidMonitorTip.setNumber("goome-" + (random.nextInt(10000) + 10000));
            androidMonitorTip.setGroup_id(orgId);
            androidMonitorTip.setCatalogue_id(cataId);
            androidMonitorTip.setCID(monitorTips.getCID());
            androidMonitorTip.setPhone(System.currentTimeMillis() + "");
            androidMonitorTip.setAliase(monitorTips.getDeviceNumber());
            androidMonitorTip.setDev_number(monitorTips.getDeviceNumber());
            androidMonitorTip.setDev_name(monitorTips.getDeviceName());
            androidMonitorTip.setDev_type(devType[random.nextInt(4)]);
            androidMonitorTip.setAlarm_type_id(Integer.parseInt(alarmType[random.nextInt(23)]));
            androidMonitorTip.setOnline(random.nextBoolean());
            androidMonitorTip.setSatellites(random.nextInt(11));
            androidMonitorTip.setModel_id(random.nextInt(42) + "");
            androidMonitorTip.setModel_type(monitorTips.getModelType());
            androidMonitorTip.setModel_name(monitorTips.getModelName());
            androidMonitorTip.setActivation(random.nextBoolean());
            androidMonitorTip.setDisabled(random.nextBoolean());
            androidMonitorTip.setAttention(random.nextBoolean());
            androidMonitorTip.setOnline_list(monitorTips.getOnlineList());


            androidMonitorTip.setLat(monitorTips.getLat());
            androidMonitorTip.setLng(monitorTips.getLng());
            androidMonitorTip.setAddress("");


            androidMonitorTip.setCommond(monitorTips.getCommond());
            androidMonitorTip.setSpeed(monitorTips.getSpeed());
            androidMonitorTip.setOwner("用户 " + monitorTips.getCID());
            androidMonitorTip.setTel(System.currentTimeMillis() + "");
            androidMonitorTip.setGroup_name(monitorTips.getDeviceName());
            androidMonitorTip.setReceive_time(System.currentTimeMillis() + "");
            androidMonitorTip.setStatus("");
            androidMonitorTip.setBattery_life(monitorTips.getElectricity() + "");
            androidMonitorTip.setSignal(monitorTips.getSignal());

            androidMonitorTip.setLatlng_arraylist(monitorTips.getLatlngarraylist());

            androidMonitorTip.setLocation_time(System.currentTimeMillis() + "");
            androidMonitorTip.setSend_time(System.currentTimeMillis() + "");
            androidMonitorTip.setAlarm_time(System.currentTimeMillis() + "");
            androidMonitorTip.setEnable_time("");
            androidMonitorTip.set_enable(random.nextBoolean());
            androidMonitorTip.setEfence_support(random.nextBoolean());

            androidMonitorTip.setGps_status("0");
            androidMonitorTip.setGps_time(System.currentTimeMillis() + "");
            androidMonitorTip.setSys_time(System.currentTimeMillis() + "");
            androidMonitorTip.setIn_time(System.currentTimeMillis() + "");
            androidMonitorTip.setOut_time(System.currentTimeMillis() + "");
            androidMonitorTip.setReceive_time(System.currentTimeMillis() + "");
            androidMonitorTip.setRemark("");
            androidMonitorTip.setClient_product_type("");
            androidMonitorTip.setGoome_card("");
            androidMonitorTip.setIs_iot_card("0");
            androidMonitorTip.setDir("");
            androidMonitorTip.setDevice_info("1");
            androidMonitorTip.setDevice_info_new("1");
            androidMonitorTip.setSeconds("0");
            androidMonitorTip.setCourse("0");
            androidMonitorTip.setAcc("0");
            androidMonitorTip.setAcc_seconds("0");
            androidMonitorTip.setVoice_gid("0");
            androidMonitorTip.setVoice_status("0");
            androidMonitorTip.setSmart_record("0");
            androidMonitorTip.setTrickle_power("-1");
            androidMonitorTip.setRecord_len("0");
            androidMonitorTip.setPos_accuracy("-218121568");
            androidMonitorTip.setSrec_volume("0");
            JedisUtil6800.setString4(orgId + ":" + cataId + ":" + monitorTips.getDeviceNumber(), JSON.toJSONString(androidMonitorTip));
        }
        System.out.println("over");
    }


    /**
     * 赋值 4 5 库 省会名字
     */
    public static void mainm(String[] args) throws InterruptedException {
        int count = 1;
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(11);
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            Thread.sleep(50);
            List<String> stringList = HttpRoute.getAddressByLocation(androidMonitorTip.getLat(), androidMonitorTip.getLng());
            androidMonitorTip.setAddress(stringList.get(0));
            androidMonitorTip.setProvince(stringList.get(1));
            androidMonitorTip.setCity(stringList.get(2));
            JedisUtil6800.setString11(androidMonitorTip.getAliase(), JSON.toJSONString(androidMonitorTip));
            System.out.println("解决了 " + (count++) + " 个 ");
        }
        System.out.println("先OVER");
//        List<MonitorTips> monitorTipsList = JedisUtil6500.getMonitorTipsAll(5);
//        for (MonitorTips monitorTips : monitorTipsList) {
//            List<String> stringList = HttpRoute.getAdressByLocation(monitorTips.getLat(), monitorTips.getLng());
//            monitorTips.setAddress(stringList.get(0));
//            monitorTips.setProvince(stringList.get(1));
//            monitorTips.setCity(stringList.get(2));
//            System.out.println("解决了 " + (count++) + " 个 ");
//            JedisUtil6500.setString5(monitorTips.getOrgnizationId() + ":" + monitorTips.getCatalogueId() + ":" + monitorTips.getAliase(), JSON.toJSONString(monitorTips));
//        }
    }

    /**
     * 创建用户9库 以及所拥有的组id
     */
    public static void main92(String args[]) {
        List<Integer> list = new ArrayList<>();
        int level = 5;
        int group = 101;
        for (int i = 1; i <= 100; i++) {
            UserOrg userOrg = new UserOrg();
            userOrg.setImgName("");
            list.add(group);
            list.add(group + 1);
            list.add(group + 2);
            list.add(group + 3);
            userOrg.setOrgnizationIds(JSON.toJSONString(list));
            list.clear();
            group += 4;
            userOrg.setParentId(0 + "");
            userOrg.setParentName("admin");
            userOrg.setCreateTime(System.currentTimeMillis() + "");
            userOrg.setUser_id(i + "");
            userOrg.setOrgName("用户" + i);
            userOrg.setUserName("用户" + i);
            userOrg.setRemark("");
            userOrg.setSeq(random.nextInt(88));
            userOrg.setRoleOrgModels("");
            userOrg.setLevel(level);
            userOrg.setImgName("用户" + i);
            userOrg.setPassword("123456");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            userOrg.setImgName(i + "");
            System.out.println("name : " + i);
            if (i == 100) {
                userOrg.setLevel(level + 1);
                userOrg.setOrgName("admin");
                userOrg.setParentName("");
                userOrg.setUserName("admin");
            }
            JedisUtil6800.setString9(i + "", JSON.toJSONString(userOrg));
        }


    }


    /**
     * 操作9库  添加子用户
     */
    public static void main755(String args[]) {
        List<Integer> list = new ArrayList<>();
//        List<UserOrg> userOrgList = JedisUtil6491.getUserOrgAll(9);
//        for (UserOrg userOrg : userOrgList) {
//            list.clear();
        list.add(random.nextInt(100) + 1);
        list.add(random.nextInt(100) + 1);
        list.add(random.nextInt(100) + 1);
        list.add(random.nextInt(100) + 1);
        list.add(random.nextInt(100) + 1);
        list.add(random.nextInt(100) + 1);
//            String ImgName = userOrg.getImgName();
//            if ("admin".equals(ImgName)) {
//                userOrg.setUser_id(88888888);
//                userOrg.setSon_ids(JSON.toJSONString(list));
//                JedisUtil6491.setString9("admin", JSON.toJSONString(userOrg));
//            } else {
//                userOrg.setUser_id(Integer.parseInt(ImgName));
//                userOrg.setSon_ids(JSON.toJSONString(list));
//                JedisUtil6491.setString9(userOrg.getUser_id() + "", JSON.toJSONString(userOrg));
//            }
//        }
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9("1"), UserOrg.class);
        userOrg.setUser_id(102 + "");
        userOrg.setParentName("admin");
        userOrg.setOrgName("迁西中关村");
        userOrg.setUserName("迁西中关村");
        userOrg.setCreateTime(System.currentTimeMillis() + "");
        userOrg.setSon_ids(JSON.toJSONString(list));
        JedisUtil6800.setString9("迁西中关村", JSON.toJSONString(userOrg));


    }


    /**
     * 创造经纬度
     */
    public static void main444(String[] args) {
        List<MonitorTips> monitorTipsList = JedisUtil6800.getMonitorTipsByCatalogId("1");
        for (MonitorTips monitorTips : monitorTipsList) {
            JSONArray jsonArray = JSONArray.parseArray(monitorTips.getLatlngarraylist());
            List<MonitorTips> monitorTipsList1 = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                String str = (String) jsonArray.get(i);
                String[] bt = str.split(",");
                monitorTips.setLat(bt[0]);
                monitorTips.setLng(bt[1]);
                monitorTips.setLocationTime((Long.valueOf(monitorTips.getLocationTime()) + 10000) + "");
                monitorTipsList1.add(monitorTips);
            }
        }
    }

//3/6 20：20 开始设备大转移---------------------------------------------------------------------------------------

    /**
     * 创建 用户
     */
    public static void maink(String[] args) {
        List<String> orgnizationIds = new ArrayList<>();
        List<String> fence_ids = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(random.nextInt(100));
            list.add(random.nextInt(100));
            list.add(random.nextInt(100));
            list.add(random.nextInt(100));
            UserOrg userOrg = new UserOrg();
            userOrg.setCreateTime(TimeUtils.getGlnz());
            userOrg.setLevel(5);
            userOrg.setPassword("111");
            userOrg.setParentId("admin");
            userOrg.setParentName("admin");
            userOrg.setUserName("李" + i);
            userOrg.setSon_ids(JSON.toJSONString(list));
            fence_ids.add(KeyUtil.genUniqueKey());
            userOrg.setFence_ids("");
            userOrg.setUser_id(i + "");
            for (int j = 0; j < 4; j++) {
                orgnizationIds.add(KeyUtil.genUniqueKey());
            }
            userOrg.setOrgnizationIds(JSON.toJSONString(orgnizationIds));
            orgnizationIds.clear();
            list.clear();
            fence_ids.clear();
            JedisUtil6800.setString9(i + "", JSON.toJSONString(userOrg));
        }
    }

    /**
     * 修改用户9库
     */
    public static void mainf(String[] args) {
        List<String> orgnizationIds = new ArrayList<>();
        List<String> fence_ids = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(random.nextInt(100));
        list.add(random.nextInt(100));
        list.add(random.nextInt(100));
        list.add(random.nextInt(100));
        UserOrg userOrg = new UserOrg();
        userOrg.setCreateTime(TimeUtils.getGlnz());
        userOrg.setLevel(6);
        userOrg.setPassword("111");
        userOrg.setParentId("admin");
        userOrg.setParentName("admin");
        userOrg.setUserName("迁西中关村");
        userOrg.setUser_id("101");
        userOrg.setSon_ids(JSON.toJSONString(list));
        fence_ids.add("");
        userOrg.setFence_ids(JSON.toJSONString(fence_ids));
        for (int j = 0; j < 4; j++) {
            orgnizationIds.add(KeyUtil.genUniqueKey());
        }
        userOrg.setOrgnizationIds(JSON.toJSONString(orgnizationIds));
        JedisUtil6800.setString9("迁西中关村", JSON.toJSONString(userOrg));
    }

    /**
     * 创建 组
     */
    public static void maingg(String[] args) {
        int orgs = 399;  //101-500
        int catas = 1599;//1  -1600
        int count = 1;
        int userId = 1;
        List<String> catalogue_ids = new ArrayList<>();
        List<String> fence_ids = new ArrayList<>();
        List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);// 101
        for (UserOrg userOrg : userOrgList) {// 用户
            if (UserSetting.ADMINID.equals(userOrg.getUserName())) {
                continue;
            }
            JSONArray jsonArray = JSONArray.parseArray(userOrg.getOrgnizationIds());
            for (Object obj : jsonArray) {// 4个 组号
                Organization organization = new Organization();
                organization.setParent_id(userOrg.getUser_id());
                organization.setCreat_time(TimeUtils.getGlnz());
                organization.setOrganization_id(obj + "");
                organization.setOrganization_name(RandomStringUtils.randomAlphanumeric(11));
                fence_ids.add(KeyUtil.genUniqueKey());
                organization.setFence_ids(JSON.toJSONString(fence_ids));
                organization.setRemark("一个用户4个组，一个组1个围栏，一个组4个目录，一个目录22个设备");
                for (int i = 0; i < 4; i++) {
                    catalogue_ids.add(KeyUtil.genUniqueKey());
                }
                organization.setCatalogue_ids(JSON.toJSONString(catalogue_ids));
                catalogue_ids.clear();
                fence_ids.clear();
                JedisUtil6800.setString6(organization.getOrganization_id(), JSON.toJSONString(organization));
            }
        }
    }

    /**
     * 创建目录
     */
    public static void maine(String[] args) {
        int start = 1111100000;
        int deviceId = 0;
        List<Organization> organizations = JedisUtil6800.getOrganizationsAll(6);//404
        List<String> fence_ids = new ArrayList<>();
        List<String> device_ids = new ArrayList<>();
        for (Organization organization : organizations) {//404
            JSONArray jsonArray = JSONArray.parseArray(organization.getCatalogue_ids());
            for (Object obj : jsonArray) {//4
                Catalog catalog = new Catalog();
                catalog.setParent_id(organization.getOrganization_id());
                catalog.setCatalog_id(obj + "");
                catalog.setCatalog_name(RandomStringUtils.randomAlphanumeric(10));
                catalog.setFence_ids(organization.getFence_ids());
                catalog.setCreat_time(TimeUtils.getGlnz());
                catalog.setRemark("");
                for (int i = 0; i <= 22; i++) {
                    deviceId = start + i;
                    device_ids.add(deviceId + "");
                }
                start = start + 23;
                catalog.setDevice_ids(JSON.toJSONString(device_ids));
                device_ids.clear();
                JedisUtil6800.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));
            }
        }
    }

    /**
     * 创建围栏 和设备
     */
    public static void mainrg(String[] args) {
        List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);
        List<String> cataList = new ArrayList<>();
        List<String> devList = new ArrayList<>();
        List<String> orgList = new ArrayList<>();
//        List<String> fences = new ArrayList<>();
        JSONArray cataArray = null;
        JSONArray fences = null;
        for (UserOrg userOrg : userOrgList) {//100 个用户
            if (UserSetting.ADMINID.equals(userOrg.getUserName()) || UserSetting.QIANXI.equals(userOrg.getUserName())) {
                continue;
            }
            JSONArray orgArray = JSONArray.parseArray(userOrg.getOrgnizationIds());//4个组
            for (Object orgId : orgArray) {
                Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);
                fences = JSONArray.parseArray(organization.getFence_ids());
                cataArray = JSONArray.parseArray(organization.getCatalogue_ids());//4个目录
                //设备 start
                for (Object cataId : cataArray) {//4个 目录
                    Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);
                    catalog.setFence_ids(JSON.toJSONString(fences));
                    JSONArray devArray = JSONArray.parseArray(catalog.getDevice_ids());
//                    AndroidMonitorTip androidMonitorTip=JSONObject.parseObject(JedisUtil6500.getString4(devArray.get(0)+""),AndroidMonitorTip.class);
//                    JSONArray fences=JSONArray.parseArray(catalog.getFence_ids());
                    for (Object devId : devArray) {//23个设备
//                        creatAndoridDev(devId + "", userOrg.getUser_id(), orgId + "", organization.getOrganization_name(), cataId + "", catalog.getCatalog_name(), catalog.getFence_ids());
                        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(devId + ""), AndroidMonitorTip.class);
                        if (androidMonitorTip == null) {
                            System.out.println(devId + " 为空 ");
                            continue;
                        }
                        androidMonitorTip.setGroup_id(organization.getOrganization_id());
                        androidMonitorTip.setGroup_name(organization.getOrganization_name());
                        androidMonitorTip.setCatalogue_id(catalog.getCatalog_id());
                        androidMonitorTip.setCata_name(catalog.getCatalog_name());
                        if (androidMonitorTip.isBind_fence()) {
                            androidMonitorTip.setFence_id(catalog.getFence_ids());
                        }
                        JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
                    }
                }
                //设备over
                //围栏start
//                Catalog catalog = JSONObject.parseObject(JedisUtil6500.getString7(cataArray.get(0) + ""), Catalog.class);
//                JSONArray devArray = JSONArray.parseArray(catalog.getDevice_ids());
//                AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6500.getString11(devArray.get(0) + ""), AndroidMonitorTip.class);
//                creatFence(null, userOrg.getUser_id(), orgId + "", cataArray.toJSONString(), fences.get(0) + "", androidMonitorTip.getLng(), androidMonitorTip.getLat());
                //围栏over
            }
            System.out.println("OVER");
        }
    }

    public static void creatFence(String devId, String userId, String orgId, String cataIdList, String fence_id, String lng, String lat) {
        List<String> orgList = new ArrayList<>();
//        List<String> cataList = new ArrayList<>();
//        List<String> devList = new ArrayList<>();
        orgList.add(orgId);
//        cataList.add(cataId);
//        devList.add(null);
        String data = lng + "|" + lat + ",300.0";
        Fence fence = new Fence();
        fence.setFenceName(RandomStringUtils.randomAlphanumeric(11));
        fence.setFenceData(data);
        fence.setId(fence_id);
        fence.setFenceType(1);
        fence.setEnabled(true);
        fence.setOrganizationId(JSON.toJSONString(orgList));
        fence.setCatalogueIds(cataIdList);
        fence.setFenceTypeText("圆形");
        fence.setUserId(userId);
        JedisUtil6800.setString8(fence_id, JSON.toJSONString(fence));
    }

    public static void creatAndoridDev(String devId, String userId, String orgId, String orgName, String cataId, String cataName, String fence_id) {
        String[] bindType = new String[]{"1", "2", "3"};
        String[] modelType = new String[]{"0", "1", "11", "10"};// 无线 有线类型
        String[] devType = new String[]{"GT06S", "GM03C", "GS03B", "GM02A+"};
        String[] alarmType = new String[]{"0", "1", "2", "3", "4", "5", "6", "8", "9", "24", "31", "32", "33", "34", "36", "40", "41", "42", "44", "45", "46", "47", "48", "49"};
        List<Integer> onlineList = new ArrayList<>();
//        AndroidMonitorTip androidMonitorTip = JedisUtil6500.getAndroidMonitorTipsByDeviceId(devId);
//        if (androidMonitorTip != null) {
        AndroidMonitorTip androidMonitorTip = new AndroidMonitorTip();
        androidMonitorTip.setUser_id(userId);
        androidMonitorTip.setGroup_id(orgId);
        androidMonitorTip.setGroup_name(orgName);
        androidMonitorTip.setCatalogue_id(cataId);
        androidMonitorTip.setCata_name(cataName);
        androidMonitorTip.setDev_number(devId);
        androidMonitorTip.setAliase(devId);
        androidMonitorTip.setDev_name(devId);
        androidMonitorTip.setNumber("goome-" + (random.nextInt(10000) + 10000));
        androidMonitorTip.setImei(LocationUtils.getIMEI());
        androidMonitorTip.setImsi(LocationUtils.getImsi());
        androidMonitorTip.setIccid(LocationUtils.getIMEI());
        androidMonitorTip.setAlarm_type_id(Integer.parseInt(alarmType[random.nextInt(24)]));
        androidMonitorTip.setPhone(System.currentTimeMillis() + "");
        androidMonitorTip.setBattery_life(random.nextInt(100) + "");
        androidMonitorTip.setActivation(random.nextBoolean());
        androidMonitorTip.setSatellites(random.nextInt(11));
        androidMonitorTip.setModel_id(random.nextInt(42) + "");
        androidMonitorTip.setModel_type(modelType[random.nextInt(4)]);
        androidMonitorTip.setDev_type(devType[random.nextInt(4)]);
        androidMonitorTip.setDisabled(false);//未过期
        boolean bind = random.nextBoolean();
        androidMonitorTip.setBind_fence(bind);
        if (androidMonitorTip.isActivation()) {//是激活状态
            androidMonitorTip.setAttention(random.nextBoolean());//是否关注
            androidMonitorTip.setEnable_time(TimeUtils.getPreTimeByDay(200, 2));//过期时间
            androidMonitorTip.setOnline(random.nextBoolean());//在线
            androidMonitorTip.setVoltage(random.nextInt(90) + "");//电压
            androidMonitorTip.setStatus("NotDisassembly, ContinueLocation, ACCOFF, OilON, EleON");
            androidMonitorTip.setMileage(random.nextInt(900));
            androidMonitorTip.setLast_update_time(TimeUtils.getGlnz());
            androidMonitorTip.setAlarm_time(TimeUtils.getGlnz());
            androidMonitorTip.setSend_time(TimeUtils.getGlnz());
            androidMonitorTip.setLocation_type(random.nextInt(4) + "");
            androidMonitorTip.setLocation_time(TimeUtils.getGlnz());
            androidMonitorTip.setSend_time(TimeUtils.getGlnz());
            androidMonitorTip.setGPS_direct(random.nextInt(100) + "");
            androidMonitorTip.setSignal(random.nextInt(100));
            androidMonitorTip.setSpeed(random.nextInt(180) + "");
            androidMonitorTip.set_enable(true);//可用
            androidMonitorTip.setDisabled(false);//未过期

            for (int i = 0; i < 9; i++) {
                onlineList.add(random.nextInt(2));
            }
            androidMonitorTip.setOnline_list(JSON.toJSONString(onlineList));
            onlineList.clear();

            if (bind) {//绑定
                androidMonitorTip.setEfence_support(bind);
                androidMonitorTip.setBind_fence(bind);
                androidMonitorTip.setFence_alarm_type(bindType[random.nextInt(3)]);
                androidMonitorTip.setFence_id(fence_id);
            } else {//未绑定
                androidMonitorTip.setEfence_support(false);//不支持围栏
                androidMonitorTip.setBind_fence(false);// 无关联
                androidMonitorTip.setFence_alarm_type("0");
            }

        } else {//未激活 也就没关联
            androidMonitorTip.setBind_fence(false);
            androidMonitorTip.setFence_alarm_type("0");
        }

        androidMonitorTip.setGps_status("0");
        androidMonitorTip.setGps_time(System.currentTimeMillis() + "");
        androidMonitorTip.setSys_time(System.currentTimeMillis() + "");
        androidMonitorTip.setIn_time(System.currentTimeMillis() + "");
        androidMonitorTip.setOut_time(System.currentTimeMillis() + "");
        androidMonitorTip.setReceive_time(System.currentTimeMillis() + "");
        androidMonitorTip.setRemark("");
        androidMonitorTip.setClient_product_type("");
        androidMonitorTip.setGoome_card("");
        androidMonitorTip.setIs_iot_card("0");
        androidMonitorTip.setDir("");
        androidMonitorTip.setDevice_info("1");
        androidMonitorTip.setDevice_info_new("1");
        androidMonitorTip.setSeconds("0");
        androidMonitorTip.setCourse("0");
        androidMonitorTip.setAcc("0");
        androidMonitorTip.setAcc_seconds("0");
        androidMonitorTip.setVoice_gid("0");
        androidMonitorTip.setVoice_status("0");
        androidMonitorTip.setSmart_record("0");
        androidMonitorTip.setTrickle_power("-1");
        androidMonitorTip.setRecord_len("0");
        androidMonitorTip.setPos_accuracy("-218121568");
        androidMonitorTip.setSrec_volume("0");

        JedisUtil6800.setString11(devId, JSON.toJSONString(androidMonitorTip));
    }

    public static void mainbn(String[] args) {
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9("1"), UserOrg.class);
        JSONArray jsonArray = JSONArray.parseArray(userOrg.getFence_ids());
        //["1583500780272918471","1583500780273304540","1583500780273952617","1583500780274363263","1583836235516385294"]
        System.out.println(jsonArray);
        jsonArray.remove(4);
        System.out.println(jsonArray);
        userOrg.setFence_ids(JSON.toJSONString(jsonArray));
        JedisUtil6800.setString9(userOrg.getUser_id(), JSON.toJSONString(userOrg));
//        Fence fence = JSONObject.parseObject(JedisUtil6500.getString8("1583836235516385294"), Fence.class);
//        System.out.println(fence.toString());
    }

    /**
     * 建立围栏和 用户 关系
     */
    public static void mainfb(String[] args) {
        List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);
        List<Object> fences = new ArrayList<>();
        int a = 1;
        int b = 1;
        for (UserOrg userOrg : userOrgList) {//100
            if (UserSetting.ADMINID.equals(userOrg.getUserName()) || UserSetting.QIANXI.equals(userOrg.getUserName())) {
                continue;
            }
            JSONArray orgArray = JSONArray.parseArray(userOrg.getFence_ids());// 4
            for (Object obj : orgArray) {
//                Organization organization = JSONObject.parseObject(JedisUtil6500.getString6(obj + ""), Organization.class);
                Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(obj + ""), Fence.class);
//                JSONArray jsonArray = JSONArray.parseArray(organization.getFence_ids());
//                fences.addAll(jsonArray);
//                b++;
                fence.setUserId(userOrg.getUser_id());
                JedisUtil6800.setString8(fence.getId(), JSON.toJSONString(fence));
            }
//            userOrg.setFence_ids(JSON.toJSONString(fences));
//            fences.clear();
//            System.out.println("用户" + (++a));
//            System.out.println("组" + (b));
//            JedisUtil6500.setString9(userOrg.getUser_id(), JSON.toJSONString(userOrg));
        }
    }

    /*
    修改 设备归属 属性
     */
    public static void mainvb(String[] args) {
        List<AndroidMonitorTip> androidMonitorTips = JedisUtil6800.getAndroidMonitorTipsAll(4);
        List<Catalog> catalogs = JedisUtil6800.getCataloguesAll(7);
        int count = 1;
        int limit = 23;
        int start = 0;
        int end = 0;
        List<String> devs = new ArrayList<>();
        for (Catalog catalog : catalogs) {
            end = count * limit;//1*23=23
            for (; start < end; start++) {
                devs.add(androidMonitorTips.get(start).getDev_number());
            }
            catalog.setDevice_ids(JSON.toJSONString(devs));
            devs.clear();
            start = end;
            count++;
            System.out.println("cata" + count);
            JedisUtil6800.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));
        }
    }


    /**
     * 修改设备 围栏 绑定时间 属性
     */
    public static void main(String[] args) {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
//            if (androidMonitorTip.isActivation()) {
//                if (androidMonitorTip.isBind_fence()) {
//                    try {
//                        jsonArray = JSONArray.parseArray(androidMonitorTip.getFence_id());
//                        System.out.println("存在绑定" + androidMonitorTip.getDev_number() + "     " + jsonArray);
//                    } catch (Exception e) {
//                        jsonArray = new JSONArray();
//                    }
//                    int length = jsonArray.size();
//                    for (int i = 0; i < length; i++) {
//                        list.add(TimeUtils.getPreTimeByDay(-i, 2));
//                    }
//                    androidMonitorTip.setBind_time(JSON.toJSONString(list));
//                    list.clear();
//                } else {
//
//                    androidMonitorTip.setFence_id(JSON.toJSONString(list1));
//                }
//            }
//            Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(androidMonitorTip.getGroup_id()), Organization.class);
//            androidMonitorTip.setGroup_name(organization.getOrganization_name());
            if (androidMonitorTip.isActivation()) {
                if (androidMonitorTip.isOnline()) {
                    androidMonitorTip.setLast_update_time(TimeUtils.getGlnz());
                    androidMonitorTip.setSend_time(TimeUtils.getGlnz());
                    androidMonitorTip.setLocation_time(TimeUtils.getGlnz());
                    androidMonitorTip.setAlarm_time(TimeUtils.getGlnz());
                    androidMonitorTip.setReceive_time(TimeUtils.getGlnz());
                    androidMonitorTip.setAlarm_type_id(random.nextInt(42));
                    JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
                } else {
                    androidMonitorTip.setStop_time(TimeUtils.getPreTimeByDay(-random.nextInt(10), 1));
                }
            }

        }
    }

    /**
     * 删除围栏信息
     */
    public static void mainfg(String[] args) {
        //727583023385682  162206468891715
        //549195146859703  685058836386433
        List<String> list = new ArrayList<>();
        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4("685058836386433"), AndroidMonitorTip.class);
//        list.add(TimeUtils.getSdf());
//        androidMonitorTip.setBind_time(JSON.toJSONString(list));
        System.out.println(androidMonitorTip.getBind_time());
        androidMonitorTip.setBind_time(null);
        JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));

//          用户一新建的无绑定围栏 Fence{Id='1583837425163656745', FenceType=1, FenceTypeText='圆形', UserId='1', OrganizationIds='null', CatalogueIds='null'}
//        Fence fence = JSONObject.parseObject(JedisUtil6500.getString8("1583837425163656745"), Fence.class);
//        System.out.println(fence);

//        UserOrg userOrg=JSONObject.parseObject(JedisUtil6500.getString9("1"),UserOrg.class);
//        UserOrg userOrg1=JSONObject.parseObject(JedisUtil6500.getString9(UserSetting.QIANXI),UserOrg.class);
//        userOrg1.setOrgnizationIds(userOrg.getOrgnizationIds());
//        JedisUtil6500.setString9(UserSetting.QIANXI,JSON.toJSONString(userOrg1));

    }


    /**
     * 修改 组 目录 名字
     */
    public static void maindf(String[] args) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("static/json/CitysData.json");
        InputStream inputStream = classPathResource.getInputStream();
        JSONArray citys = JSON.parseArray(ReadJSONFile.readFile(inputStream));
        int count = 0;
        List<String> nameList = new ArrayList<>();
        for (int i = 0; i < citys.size(); i++) {
            JSONObject jsonObject = citys.getJSONObject(i);
            JSONArray cites = jsonObject.getJSONArray("cities");
            for (int j = 0; j < cites.size(); j++) {
                String name = cites.getJSONObject(j).getString("name");
                nameList.add(name);
                count++;
            }
        }

        System.out.println(count);

        int c = 1;
        List<Organization> organizations = JedisUtil6800.getOrganizationsAll(6);
        for (Organization organization : organizations) {
            JSONArray catas = JSONArray.parseArray(organization.getCatalogue_ids());
            String orgName = nameList.get(random.nextInt(330));
            organization.setOrganization_name(orgName);
            JedisUtil6800.setString6(organization.getOrganization_id(), JSON.toJSONString(organization));
            for (Object cataId : catas) {
                Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);
                catalog.setCatalog_name(orgName + (c++) + "车队");
                System.out.println(catalog.getCatalog_name());
                JedisUtil6800.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));
            }
        }
    }

    /**
     * 修改围栏 名字
     */
    public static void mainef(String[] args) {
        List<Fence> fenceList = JedisUtil6800.getFenceAll(8);
        String lng = "";
        String lat = "";
        List<String> address = null;
        for (Fence fence : fenceList) {
            if (fence.getFenceType() == 1) {//圆形  "fenceData": "110.687296|33.896302,300.0",
                String[] bt = fence.getFenceData().split(",");
                lng = bt[0].split("\\|")[0];
                lat = bt[0].split("\\|")[1];
            } else {//矩形  "fenceData": "118.19117583364249,39.60720984393394;118.16371001332999,39.57546170666932"
                String[] bt = fence.getFenceData().split(";");
                lng = bt[0].split(",")[0];
                lat = bt[0].split(",")[1];
            }
            try {
                address = HttpRoute.getAddressByLocation(lat, lng);
                fence.setFenceName(address.get(2) + address.get(3) + address.get(4));
                System.out.println(fence.getFenceName());
                Thread.sleep(20);
            } catch (Exception e) {
                System.out.println(fence.getId() + "  出现故障  ");
            }
            JedisUtil6800.setString8(fence.getId(), JSON.toJSONString(fence));
        }
    }

    /**
     * 把目录/统计 增添到 用户中
     */
    public static void mainfffss(String[] args) {
        List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);//admin
        List<String> list = new ArrayList<>();
        List<String> type = new ArrayList<>();

        type.add("0");
        type.add("0");
        type.add("0");
        type.add("0");
        type.add("0");
        type.add("0");
        type.add("0");

        List<DailyAlarm> dailyAlarms = new ArrayList<>();
        dailyAlarms.add(new DailyAlarm(0, TimeUtils.getPreTimeByDay(-2, 1)));//19号
        dailyAlarms.add(new DailyAlarm(0, TimeUtils.getPreTimeByDay(-3, 1)));//18号
        dailyAlarms.add(new DailyAlarm(0, TimeUtils.getPreTimeByDay(-4, 1)));//17号
        dailyAlarms.add(new DailyAlarm(0, TimeUtils.getPreTimeByDay(-5, 1)));//16号
        dailyAlarms.add(new DailyAlarm(0, TimeUtils.getPreTimeByDay(-6, 1)));//15号

        List<IncrementModel> DevIncrementModelList = new ArrayList<>();
        DevIncrementModelList.add(new IncrementModel(TimeUtils.getPreTimeByMonth(0), 0));//3月
        DevIncrementModelList.add(new IncrementModel(TimeUtils.getPreTimeByMonth(-1), 0));//2月
        DevIncrementModelList.add(new IncrementModel(TimeUtils.getPreTimeByMonth(-2), 0));//1月
        DevIncrementModelList.add(new IncrementModel(TimeUtils.getPreTimeByMonth(-3), 0));//12月
        DevIncrementModelList.add(new IncrementModel(TimeUtils.getPreTimeByMonth(-4), 0));//11月
        DevIncrementModelList.add(new IncrementModel(TimeUtils.getPreTimeByMonth(-5), 0));//10月

        List<IncrementModel> ExpireDateModelList = new ArrayList<>();
        ExpireDateModelList.add(new IncrementModel("60", 0));
        ExpireDateModelList.add(new IncrementModel("50", 0));
        ExpireDateModelList.add(new IncrementModel("40", 0));
        ExpireDateModelList.add(new IncrementModel("30", 0));
        ExpireDateModelList.add(new IncrementModel("20", 0));
        ExpireDateModelList.add(new IncrementModel("10", 0));


        for (UserOrg userOrg : userOrgList) {
//            if (UserSetting.ADMINID.equals(userOrg.getUserName()) || UserSetting.QIANXI.equals(userOrg.getUserName())) {
//                continue;
//            }
//            JSONArray orgs = JSONArray.parseArray(userOrg.getOrgnizationIds());
//            for (Object orgId : orgs) {
//                Organization organization = JSONObject.parseObject(JedisUtil6500.getString6(orgId + ""), Organization.class);
//
//                organization.setGroup_bind_time(JSON.toJSONString(list));
//                organization.setGroup_fence_alarm_type(JSON.toJSONString(type));
//                organization.setGroup_fence_ids(JSON.toJSONString(list));
//
//                JSONArray catas = JSONArray.parseArray(organization.getCatalogue_ids());
//                for (Object cataId : catas) {
//                    Catalog catalog = JSONObject.parseObject(JedisUtil6500.getString7(cataId + ""), Catalog.class);
//
//                    catalog.setGroup_bind_time(JSON.toJSONString(list));
//                    catalog.setGroup_fence_alarm_type(JSON.toJSONString(type));
//                    catalog.setGroup_fence_ids(JSON.toJSONString(list));
//                    JedisUtil6500.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));
//                }
//                JedisUtil6500.setString6(organization.getOrganization_id(), JSON.toJSONString(organization));
//            }

//        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9("admin"), UserOrg.class);//admin


            userOrg.setWiredOffline(JSON.toJSONString(type));//
            userOrg.setWirelessOffline(JSON.toJSONString(type));//
            userOrg.setWirelessOnline(JSON.toJSONString(type));//

            userOrg.setDailyAlarm(JSON.toJSONString(dailyAlarms));
            userOrg.setDevIncrementModel(JSON.toJSONString(DevIncrementModelList));
            userOrg.setUserIncrementModel(JSON.toJSONString(DevIncrementModelList));
            userOrg.setExpireDateModel(JSON.toJSONString(ExpireDateModelList));

            JedisUtil6800.setString9(userOrg.getUser_id(), JSON.toJSONString(userOrg));
//        JedisUtil6800.setString9("admin", JSON.toJSONString(userOrg));//admin
        }
    }

    /**
     * 修改 设备 围栏类型存储字段
     */
    public static void mainererer(String[] args) {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        List<String> fenveTypes = new ArrayList<>();
        List<String> bindTimes = new ArrayList<>();
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
//            if (androidMonitorTip.isActivation()) {
//                if (androidMonitorTip.isBind_fence()) {
////                    JSONArray fenceIds = JSONArray.parseArray(androidMonitorTip.getFence_id());
////                    int length = fenceIds.size();
////                    for (int i = 0; i < length; i++) {
////                        fenveTypes.add("2");
////                        bindTimes.add(TimeUtils.getSdf());
////                    }
////                    androidMonitorTip.setBind_time(JSON.toJSONString(bindTimes));
////                    androidMonitorTip.setFence_alarm_type(JSON.toJSONString(fenveTypes));
////                    System.out.println(androidMonitorTip.isEfence_support() + "    " + androidMonitorTip.isBind_fence() + "    " + androidMonitorTip.getFence_id() + "        " + androidMonitorTip.getFence_alarm_type() + "      " + androidMonitorTip.getBind_time());

            androidMonitorTip.setDevice_info_new(random.nextInt(5) + "");
            if (androidMonitorTip.isActivation()) {
//                if (androidMonitorTip.isOnline()) {
//
//                } else {
//                    androidMonitorTip.setOnline(random.nextBoolean());
//                    if (androidMonitorTip.isOnline()) {
//                        androidMonitorTip.setOnline_status(TimeUtils.getStopAndOffLine().get(0));
//                    } else {
//                        androidMonitorTip.setOnline_status(TimeUtils.getStopAndOffLine().get(2));
//
//                    }
//                }
            } else {
//                androidMonitorTip.setActivation(random.nextBoolean());
//                if (androidMonitorTip.isActivation()) {
//                    if (androidMonitorTip.isOnline()) {
//                        androidMonitorTip.setOnline_status(TimeUtils.getStopAndOffLine().get(0));
//                    } else {
//                        androidMonitorTip.setOnline_status(TimeUtils.getStopAndOffLine().get(2));
//                    }
//                }else {
//                    androidMonitorTip.setOnline_status("Unknown");
//                }
            }
            JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
////                    fenveTypes.clear();
////                    bindTimes.clear();
//                }
//            }
//            if ("0".equals(androidMonitorTip.getFence_alarm_type())) {
//                androidMonitorTip.setFence_alarm_type(JSON.toJSONString(fenveTypes));
//            }
//            JedisUtil6500.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
//            System.out.println(androidMonitorTip.getFence_alarm_type());
        }
    }

    /**
     * 测试删除围栏
     */
    public static void mainaa(String[] args) {
        Fence fence = JSONObject.parseObject(JedisUtil6800.getString8("1583500780278798428"), Fence.class);
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(fence.getUserId()), UserOrg.class);
        System.out.println(userOrg);
        JSONArray cataIds = JSONArray.parseArray(fence.getCatalogueIds());
        JSONArray orgIds = JSONArray.parseArray(fence.getOrganizationIds());
        for (Object cataId : cataIds) {
            Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);
            System.out.println(catalog);
        }

        for (Object orgId : orgIds) {
            Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);
            System.out.println(organization);
        }

    }

    /**
     * 修改 设备所属 目录名字
     */
    public static void mainefe(String[] args) {
        List<Catalog> catalogs = JedisUtil6800.getCataloguesAll(7);
        for (Catalog catalog : catalogs) {
            JSONArray devs = JSONArray.parseArray(catalog.getDevice_ids());
            for (Object devId : devs) {
                AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(devId + ""), AndroidMonitorTip.class);
                androidMonitorTip.setCata_name(catalog.getCatalog_name());
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
            }
        }
    }


    /**
     * 修改3联动存储格式
     */
    public static void mainefn(String[] args) {
        List<Organization> organizationsAll = JedisUtil6800.getOrganizationsAll(6);
        for (Organization organization1 : organizationsAll) {
            JSONArray group_fence_ids = JSONArray.parseArray(organization1.getGroup_fence_ids());
            if (group_fence_ids.size() == 0) {
                JSONArray Group_fence_alarm_type = JSONArray.parseArray(organization1.getGroup_fence_alarm_type());
//                Group_fence_alarm_type.remove(0);
                System.out.println(Group_fence_alarm_type);
                System.out.println(organization1.getGroup_bind_time());
                System.out.println(organization1.getGroup_fence_ids());
//                organization1.setGroup_fence_alarm_type(Group_fence_alarm_type.toJSONString());
//                JedisUtil6500.setString6(organization1.getOrganization_id(), JSON.toJSONString(organization1));
            }
        }

//        List<Catalog> catalogs = JedisUtil6500.getCataloguesAll(7);
//        for (Catalog catalog : catalogs) {
//            JSONArray group_fence_ids = JSONArray.parseArray(catalog.getGroup_fence_ids());
//            if (group_fence_ids.size() == 0) {
//                JSONArray Group_fence_alarm_type = JSONArray.parseArray(catalog.getGroup_fence_alarm_type());
//                Group_fence_alarm_type.remove(0);
//                System.out.println(Group_fence_alarm_type);
//                System.out.println(catalog.getGroup_bind_time());
//                System.out.println(catalog.getGroup_fence_ids());
//                catalog.setGroup_fence_alarm_type(Group_fence_alarm_type.toJSONString());
//                JedisUtil6500.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));
//            }
//        }
    }


    /**
     * 轮询  入库3库 模拟设备
     */
    public static void mainddd(String[] args) {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        List<StopDeviceDetail> stopDeviceDetailList = new ArrayList<>();

        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            if (androidMonitorTip.isActivation()) {
                StopDeviceDetail stopDeviceDetail = new StopDeviceDetail();
                BeanUtils.copyProperties(androidMonitorTip, stopDeviceDetail);
                stopDeviceDetail.setStart_time(TimeUtils.getPreTimeByDay(-1, 1));
                stopDeviceDetail.setEnd_time(TimeUtils.getGlnz());
                stopDeviceDetail.setStop_minutes(1 * UserSetting.MINUTES + "");
                stopDeviceDetailList.add(stopDeviceDetail);
                JedisUtil6800.setString3(androidMonitorTip.getDev_number(), JSON.toJSONString(stopDeviceDetailList));
                stopDeviceDetailList.clear();
            }
        }
    }

    /**
     * 轮询  入库5库    模拟记录设备报警记录
     */
    public static void mainrgg(String[] args) {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        List<Alarm> alarmList = new ArrayList<>();
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            if (androidMonitorTip.isActivation()) {
                if (androidMonitorTip.getAlarm_type_id() != 0) {
                    System.out.println(androidMonitorTip.getAlarm_type_id());
                    Alarm alarm = new Alarm();
                    BeanUtils.copyProperties(androidMonitorTip, alarm);
                    alarm.setAlarm_text(ReportService.getAlarmNameByAlarmType(alarm.getAlarm_type_id() + ""));
                    alarmList.add(alarm);
                    JedisUtil6800.setString5(androidMonitorTip.getDev_number(), JSON.toJSONString(alarmList));
                    alarmList.clear();
                }
            }
        }
    }

    /**
     * 轮询计算设备地理位置  入库  6个小时把
     */
    public static void main666(String[] args) {
        CommonService commonService = new CommonService();
        List<Distribution> distributions = null;
        List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);
        for (UserOrg user : userOrgList) {
            if (UserSetting.ADMINID.equals(user.getUserName()) || UserSetting.QIANXI.equals(user.getUserName())) {
                continue;
            }
            distributions = getDistributionByLocations(user.getUser_id(), commonService);
            user.setDistributions(JSON.toJSONString(distributions));
            JedisUtil6800.setString9(user.getUser_id(), JSON.toJSONString(user));
        }

        UserOrg admin = JSONObject.parseObject(JedisUtil6800.getString9("admin"), UserOrg.class);
        distributions = getDistributionByLocations("admin", commonService);
        admin.setDistributions(JSON.toJSONString(distributions));
        JedisUtil6800.setString9("admin", JSON.toJSONString(admin));
    }

    /**
     * 轮询计算 设备在线数量   入9库 1个小时把
     */
    public static void mainwww(String[] args) {
        MonitorService monitorService = new MonitorService();
        CommonService commonService = new CommonService();
        StatInfo statInfo = new StatInfo();
        List<AndroidMonitorTip> androidMonitorTipList = new ArrayList<>();
        List<Integer> byList = new ArrayList<>();
        List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);
        for (UserOrg userOrg : userOrgList) {
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

    }


    public static List<Distribution> getDistributionByLocations(String userId, CommonService commonService) {
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

    /**
     * {"success":true,"message":"操作成功！","code":0,"result":{"location":{"lat":"41.119883221047","lng":"122.07184792314"},"time":"2020-03-11 17:58:20"},"timestamp":1584007518143}
     * 定时一下子  更新redis库    插入influxdb 库
     */
    public static void main445(String[] args) throws InterruptedException {
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
            androidMonitorTip.setSequenceNo(6 + "");

            JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
            tags.put("tagNum", androidMonitorTip.getDev_number());
            fields = influxdbDao.getFieldMapByAndroidMonitorTips(androidMonitorTip);

            insertToInfluxdb(measurement, tags, fields);
            System.out.println((count++) + "        ->       " + androidMonitorTip.getDev_number() + " 成功 ");
            tags.clear();
            Thread.sleep(30);// 耗时30分钟
        }
    }

    /**
     * 查influxdb 库
     */
    public static void mainthr(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("startTime", "");
        map.put("endTime", "");
        map.put("DeviceNumber", "");
        //        List<AndroidMonitorTip> androidMonitorTipsByTime = queryAndroidMonitorTipsByTime(map);

        map.put("sequenceNo", "1");
        String temp = "";
        Map<String, String> tags = new HashMap<String, String>();//索引
        Map<String, Object> fields = new HashMap<>();//字段值
        int count = 1;
        String measurement = "android_monitorTips_points";
        List<AndroidMonitorTip> androidMonitorTipsBySequenceNo = queryAndroidMonitorTipsBySequenceNo(map);
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipsBySequenceNo) {
            temp = androidMonitorTip.getLat();//lng
            androidMonitorTip.setLat(androidMonitorTip.getLng());
            androidMonitorTip.setLng(temp);
            System.out.println(androidMonitorTip.getLng() + "     " + androidMonitorTip.getLat());
            androidMonitorTip.setSequenceNo(3 + "");

            tags.put("tagNum", androidMonitorTip.getDev_number());
            fields = influxdbDao.getFieldMapByAndroidMonitorTips(androidMonitorTip);
            insertToInfluxdb(measurement, tags, fields);
            System.out.println((count++) + "        ->       " + androidMonitorTip.getDev_number() + " 成功 ");
        }
    }

    public static void insertToInfluxdb(String measurement, Map<String, String> tags, Map<String, Object> fields) {
        influxdbDao.insert(measurement, tags, fields);
    }

    public static List<AndroidMonitorTip> queryAndroidMonitorTipsByTime(Map<String, String> map) {
        return influxdbDao.queryAndroidMonitorTipsByTime(map);
    }

    public static List<AndroidMonitorTip> queryAndroidMonitorTipsBySequenceNo(Map<String, String> map) {
        return influxdbDao.queryAndroidMonitorTipsBySequenceNo(map);
    }

    /**
     * 从1库中 获取设备历史轨迹
     */
    public static void mainkk(String[] args) {
        //[30.082525,103.85656, 30.098705,104.34326, 30.114883,104.82997, 30.131063,105.31667, 30.147243,105.803375, 30.163422,106.290085, 30.179602,106.77679, 30.195782,107.26349, 30.21196,107.75019, 30.22814,108.2369, 30.24432,108.7236, 30.260498,109.210304, 30.276678,109.697014, 30.292858,110.183716, 30.309036,110.67042, 30.325216,111.15712, 30.341396,111.64383, 30.357574,112.13053, 30.373755,112.61723, 30.389935,113.10394, 30.406113,113.590645, 30.422293,114.07735, 30.438473,114.56406, 30.45465,115.05076, 30.47083,115.53746, 30.487011,116.02417, 30.50319,116.51087, 30.51937,116.997574, 30.53555,117.484276, 30.551727,117.970985, 30.567907,118.45769, 30.567907,118.45769, 30.567907,118.45769]
        List<String> strings = JedisUtil6800.getAndroidMonitorTipsLngLatArrayLisByDeviceId("100107093144618");
        System.out.println(strings);
        for (int i = 0; i < strings.size(); i++) {
            String latString = strings.get(i).split(",")[0];
            String lngString = strings.get(i).split(",")[1];
            System.out.println(latString + " " + lngString);
        }
    }

    /**
     * 检测是否是存在设备号
     */
    public static void mainwdfg(String[] args) {
//        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4("161420110050009"), AndroidMonitorTip.class);
//        System.out.println(androidMonitorTip);
//        UserOrg userOrg1 = JSONObject.parseObject(JedisUtil6800.getString9("admin"), UserOrg.class);
//        userOrg1.setUserName("ADMIN");
//        JedisUtil6800.setString9("ADMIN", JSONObject.toJSONString(userOrg1));

        //1584671979279729865
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9("1584671979279729865"), UserOrg.class);
        userOrg.setUserName("admin");
        userOrg.setParentName("ADMIN");
        userOrg.setParentId("ADMIN");
        JedisUtil6800.setString9("admin", JSONObject.toJSONString(userOrg));


    }

    /**
     * 添加日期list
     */
    public static void mainessf(String[] args) {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        LinkedList<String> linkedList = new LinkedList();
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            List<String> stringList = JedisUtil6800.getAndroidMonitorTipsLngLatArrayLisByDeviceId(androidMonitorTip.getDev_number());
            if (stringList != null) {
                int length = stringList.size();
                for (int i = 0; i < length; i++) {
                    String time = TimeUtils.getPreTimeByDay(-(i + 1), 1);
                    linkedList.addFirst(time);
                    JedisUtil6800.setString1SetLoncationTime(androidMonitorTip.getDev_number(), "LocationTime", linkedList);
                }
                linkedList.clear();
            }
            System.out.println(androidMonitorTip.getDev_number() + " OVER ");
        }
    }

    /**
     * 修改设备属性值
     */
    public static void maingfd(String[] args) throws InterruptedException {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
//            String PlatNo = "D-125" + random.nextInt(10000);
//            androidMonitorTip.setPlateNo(PlatNo);
//            JedisUtil6800.setString13(PlatNo, androidMonitorTip.getDev_number());
            androidMonitorTip.setMf_date(TimeUtils.getPreTimeByDay(-35, 1));
            JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
            System.out.println(androidMonitorTip.getDev_number() + "OVER");
        }
    }

    /**
     * 修改指令下发结果bean
     *
     * @param args
     */
    public static void mainolki(String[] args) {
        List<CmdRecord> cmdRecordList = JSON.parseArray(JedisUtil6800.getString10("567568549603653"), CmdRecord.class);
        for (CmdRecord cmdRecord : cmdRecordList) {
            if ("BP07".equals(cmdRecord.getCmdKey())) {
                cmdRecord.setCmdStatus(1);
                cmdRecord.setResponseTime(TimeUtils.getGlnz());
                cmdRecord.setResultText("处理成功");
                JedisUtil6800.setString10("567568549603653", JSON.toJSONString(cmdRecordList));

            }
        }

    }


}
