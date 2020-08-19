package com.car.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.bean.Catalog;
import com.car.bean.Organization;
import com.car.bean.UserOrg;
import com.car.domain.Alarm;
import com.car.domain.Fence;
import com.car.domain.StopDeviceDetail;
import com.car.redis.JedisUtil6800;
import com.car.setting.UserSetting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/8  17:29
 * @Description
 */
@Service
public class CommonService {


    public List<AndroidMonitorTip> getAndroidMonitorTipsByUserIdOrOrgIdOrCataId(String id) {
        String userStr = JedisUtil6800.getString9(id);
        List<AndroidMonitorTip> androidMonitorTipList = null;
        if (userStr != null) {
            System.out.println("获取 用户  " + id + "  的设备");
            androidMonitorTipList = getAndroidMonitorTipsByUserId(id);
        } else {
            String orgStr = JedisUtil6800.getString6(id);
            if (orgStr != null) {
                System.out.println("获取 组  " + id + "  的设备");
                androidMonitorTipList = getAndroidMonitorTipsByOrgId(id);
            } else {
                String cataStr = JedisUtil6800.getString7(id);
                if (cataStr != null) {
                    System.out.println("获取 目录  " + id + "  的设备");
                    androidMonitorTipList = getAndroidMonitorTipsByCatalogId(id);
                }
            }
        }
        return androidMonitorTipList;
    }

    public List<AndroidMonitorTip> getAndroidMonitorTipsByUserId(String userId) {
        List<AndroidMonitorTip> androidMonitorTipList = new ArrayList<>();
        if (UserSetting.ADMIN.equals(userId)) {
            return JedisUtil6800.getAndroidMonitorTipsAll(4);
        }
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);//1 2 admin
        if (userOrg != null) {
            JSONArray jsonArray = JSONArray.parseArray(userOrg.getOrgnizationIds());//
            for (Object orgId : jsonArray) {
                List<AndroidMonitorTip> orgList = getAndroidMonitorTipsByOrgId(orgId + "");
                androidMonitorTipList.addAll(orgList);
            }
        }
        return androidMonitorTipList;
    }

    public List<AndroidMonitorTip> getAndroidMonitorTipsByOrgId(String orgId) {
        List<AndroidMonitorTip> androidMonitorTipList = new ArrayList<>();
        Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId), Organization.class);
        JSONArray cataArray = JSONArray.parseArray(organization.getCatalogue_ids());
        for (Object cataId : cataArray) {
            List<AndroidMonitorTip> cataList = getAndroidMonitorTipsByCatalogId(cataId + "");
            androidMonitorTipList.addAll(cataList);
        }
        return androidMonitorTipList;
    }

    public List<AndroidMonitorTip> getAndroidMonitorTipsByCatalogId(String cataId) {
        List<AndroidMonitorTip> androidMonitorTipList = new ArrayList<>();
        Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId), Catalog.class);
        JSONArray jsonArray = JSONArray.parseArray(catalog.getDevice_ids());
        for (Object devId : jsonArray) {
            AndroidMonitorTip androidMonitorTip = getAndroidMonitorTipByDevId(devId + "");
            androidMonitorTip.setCata_name(catalog.getCatalog_name());//设置目录名字
            androidMonitorTipList.add(androidMonitorTip);
        }
        return androidMonitorTipList;
    }

    public AndroidMonitorTip getAndroidMonitorTipByDevId(String devId) {
        String dev = JedisUtil6800.getString4(devId);
        AndroidMonitorTip androidMonitorTip = null;
        if (dev != null) {
            androidMonitorTip = JSONObject.parseObject(dev, AndroidMonitorTip.class);
        }
        return androidMonitorTip;

    }

    /**
     * 从前端得到具体的id类型
     */
    @Transactional
    public Map<String, Object> getIdByMap(Map<String, Object> map) {
        String id = (String) map.get("OrgId");
        String userId = (String) map.get("userId");
        System.out.println("用户 : " + userId);
        if (id == null) {
            return map;
        }
        map.put("CataName", "");
        map.put("Username", "");
        map.put("OrgName", "");
        map.put("UserId", null);
        map.put("CataId", null);
        map.put("OrgId", null);

        if (JedisUtil6800.existsString6(id)) {
            Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(id), Organization.class);
            map.put("OrgName", organization.getOrganization_name());
            System.out.println("在6组库中 : " + id);
            map.put("OrgId", id);
        } else if (JedisUtil6800.existsString7(id)) {
            map.put("CataId", id);
            Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(id), Catalog.class);
            map.put("CataName", catalog.getCatalog_name());
            System.out.println("在7目录库中 : " + id);
        } else if (JedisUtil6800.existsString9(id)) {
            map.put("UserId", id);
            UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(id), UserOrg.class);
            map.put("Username", userOrg.getUserName());
            System.out.println("在9用户库中 : " + id);
        }
        return map;
    }

    public int isContains(JSONArray jsonArray, String data) {
        if (jsonArray == null) {
            return -1;
        }
        int length = jsonArray.size();
        for (int i = 0; i < length; i++) {
            if (jsonArray.get(i).toString().equals(data)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获得5库中的 根据用户id 获取报警类型
     */
    public List<Alarm> getAlarmAndroidMonitorTipsByUserId(String userId) {
        List<Alarm> alarmList = new ArrayList<>();
        if (UserSetting.ADMINID.equals(userId)) {
            return JedisUtil6800.getAlarmAndroidMonitorTipsAll(5);
        }
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);//1 2 admin
        if (userOrg != null) {
            JSONArray orgs = JSONArray.parseArray(userOrg.getOrgnizationIds());//
            for (Object orgId : orgs) {
                Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);
                JSONArray catas = JSONArray.parseArray(organization.getCatalogue_ids());
                for (Object cataId : catas) {
                    Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);
                    JSONArray devs = JSONArray.parseArray(catalog.getDevice_ids());
                    for (Object devId : devs) {
                        String strAlarms = JedisUtil6800.getString5(devId + "");
                        if (strAlarms != null) {
                            List<Alarm> alarms = JSON.parseArray(strAlarms, Alarm.class);
                            alarmList.addAll(alarms);
                        }
                    }
                }
            }
        }
        return alarmList;
    }

    /**
     * 从3库 中获得 设备停留详情
     */
    public List<StopDeviceDetail> getStopDeviceDetailsByDevId(String devId) {
        String strStop = JedisUtil6800.getString3(devId);
        List<StopDeviceDetail> stopDeviceDetailList = new ArrayList<>();
        if (strStop != null) {
            List<StopDeviceDetail> stopDeviceDetails = JSON.parseArray(strStop, StopDeviceDetail.class);
            stopDeviceDetailList.addAll(stopDeviceDetails);
        }
        return stopDeviceDetailList;
    }


    public static void main(String[] args) {
//        System.out.println(getAndroidMonitorTipsByUserId("admin").size());
//        System.out.println(getAndroidMonitorTipsByUserId("2").size());
//        System.out.println(getAndroidMonitorTipsByUserId("迁西中关村").size());
//        System.out.println(getAndroidMonitorTipsByOrgId("1583500312155555445").size());
//        System.out.println(getAndroidMonitorTipsByCatalogId("1583500780205246843").size());
//        System.out.println(getAndroidMonitorTipsByUserIdOrOrgIdOrCataId("1583500312155555445").size());
//        System.out.println(getAndroidMonitorTipByDevId("1111100003"));


    }


    /*
 根据设备id活得围栏信息
  */
    public Fence getFencebyId(String id) {
        String res = JedisUtil6800.getString8(id);
        Fence fence = JSONObject.parseObject(res, Fence.class);
        return fence;
    }
}
