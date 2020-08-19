package com.car.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.Alarm;
import com.car.androidbean.AndroidMonitorTip;
import com.car.bean.Catalog;
import com.car.bean.Organization;
import com.car.bean.UserOrg;
import com.car.domain.*;
import com.car.redis.JedisUtil6800;
import com.car.setting.UserSetting;
import com.car.utils.KeyUtil;
import com.car.utils.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.remote.rmi._RMIConnection_Stub;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:52
 * @Description
 */
@Service
public class FenceService {

    @Autowired
    CommonService commonService;


    /*
   limit: 15
   start: 0
   page: 1
   OrgId: "168c3200-a1dd-473c-9d30-a8230120604d"
   IsBind:  0 代表所有  1  已关联   2未关联
   FenceId: "a7f742d5-807b-472e-87a8-aafa013d87a0"
   WithChildren: "true" false
   Key: "跟个更"  只能查到 自己的所属设备群中的一个
     */

    public FenceDevices getDeviceFenceListById(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        String orgId = (String) map.get("OrgId");
        String fenceId = (String) map.get("FenceId");
        String Key = (String) map.get("Key");
        String isBind = (String) map.get("IsBind");
        List<AndroidMonitorTip> androidMonitorTipList = new ArrayList<>();

        if (orgId == null || "".equals(orgId)) {
            System.out.println("orgId 为空  1 赋值  ");
            orgId = JSONArray.parseArray(JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class).getOrgnizationIds()).get(0) + "";
            map.put("OrgId", orgId);
        }

        if (Key != null && Key.length() > 0) {
            AndroidMonitorTip monitorTips = getAndroidMonitorTipById(map);
            if (monitorTips == null) {
                return null;
            }
            androidMonitorTipList.add(monitorTips);
        } else {
            System.out.println("orgId  2 " + orgId);
            androidMonitorTipList = getAndroidMonitorTipByMap(map);
            System.out.println("结果 组: " + orgId + "  绑定类型: " + isBind + " - 大小size: " + androidMonitorTipList.size());
        }

        List<FenceDevice> fenceDeviceList = new ArrayList<>();
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            FenceDevice fenceDevice = new FenceDevice();
            BeanUtils.copyProperties(androidMonitorTip, fenceDevice);
            fenceDevice.setAlarm_type_text(ReportService.getAlarmNameByAlarmType(androidMonitorTip.getAlarm_type_id() + ""));
            JSONArray fenceIds = JSONArray.parseArray(androidMonitorTip.getFence_id());
            System.out.println("设备所关联的 fenceIds :  " + fenceIds);
            int index = commonService.isContains(fenceIds, fenceId);
            if (index == -1) {//不存在
                System.out.println("设备群对此围栏 " + fenceId + " 并无关联");
                androidMonitorTip.setFence_alarm_type("0");
            } else {//存在 还得找到对应的 type  以及 绑定时间
                System.out.println("设备群对此围栏 " + fenceId + " 关联类型 " + androidMonitorTip.getFence_alarm_type());
                JSONArray fenceType = JSONArray.parseArray(androidMonitorTip.getFence_alarm_type());
                androidMonitorTip.setFence_alarm_type(fenceType.getString(index));
            }

            fenceDevice.setFence_alarm_type_text(getFenceAlarmTextByType(androidMonitorTip.getFence_alarm_type()));
            fenceDeviceList.add(fenceDevice);
        }

        int length = fenceDeviceList.size();
        int start = 0;
        int page = 1;
        start = Integer.parseInt(map.get("start")+"");
        page = Integer.parseInt( map.get("page")+"");
        int end = page * UserSetting.LIMIT;
        end = (end > length) || (end == length) ? length - 1 : end;
        fenceDeviceList = fenceDeviceList.subList(start, end + 1);
        FenceDevices fences = new FenceDevices();
        fences.setData(fenceDeviceList);
        fences.setIsExcel(false);
        fences.setLimit(UserSetting.LIMIT);
        fences.setPage(page);
        fences.setTotal(length);
        return fences;
    }

    /*
    limit: 15
    start: 0
    page: 1
    OrgId: "G101"
    WithChildren: "true"
    FenceName: "家"
    fence/FenceListByOrg
    */
    public Fences FenceListByOrg(Map<String, Object> map) {
        String FenceName = (String) map.get("FenceName");// key
        String OrgId = (String) map.get("OrgId");
        String userId = (String) map.get("userId");
        List<Fence> fenceList = new ArrayList<>();
        Fences fences = new Fences();
        List<Fence> redisFenceList = null;
        if (UserSetting.ADMINID.equals(userId)) {// 管理员
            if (OrgId == null) {
                redisFenceList = JedisUtil6800.getFenceAll(8);
            } else {
                redisFenceList = getFenceListByUserId(OrgId);// 用户的所有围栏
                if (redisFenceList == null) {
                    redisFenceList = getFenceListByOrgIdOrCataId(OrgId);//组下 围栏
                }
            }
        } else {
            if (OrgId == null) {
                redisFenceList = getFenceListByUserId(userId);// 用户的所有围栏
            } else {
                redisFenceList = getFenceListByOrgIdOrCataId(OrgId);//组下 围栏
            }
        }

        int length = redisFenceList.size();
        System.out.println(userId + "  redisFenceList  :  " + redisFenceList.size());

        int start = 0;
        int page = 1;
        start = Integer.parseInt(map.get("start") + "");
        page = Integer.parseInt(map.get("page") + "");
        int end = page * UserSetting.LIMIT;
        //todo
        end = end > length || end == length ? length - 1 : end;

        if (FenceName != null) {
            for (Fence fence : redisFenceList) {
                if (FenceName.equals(fence.getFenceName())) {
                    fenceList.add(fence);
                    break;
                }
            }
            System.out.println("FenceName: " + FenceName);
            fences.setData(fenceList);
            fences.setIsExcel(false);
            fences.setLimit(UserSetting.LIMIT);
            fences.setPage(1);
            fences.setTotal(fenceList.size());
            return fences;
        }
        redisFenceList = redisFenceList.subList(start, end + 1);
        fences.setData(redisFenceList);
        fences.setIsExcel(false);
        fences.setLimit(UserSetting.LIMIT);
        fences.setPage(page);
        fences.setTotal(length);
        return fences;
    }

    /* 默认展示的是  用户的所有组的围栏
    从用户那里得到FenceList
     */
    public List<Fence> getFenceListByUserId(String userId) {
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        if (userOrg == null) {
            System.out.println("不是userId");
            return null;
        }
        List<Fence> fenceList = new ArrayList<>();
        JSONArray fences = JSONArray.parseArray(userOrg.getFence_ids());
        System.out.println("此 用户 下所绑定的围栏 : " + fences);
        for (Object fencdId : fences) {
            Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(fencdId + ""), Fence.class);
            fenceList.add(fence);
        }
        return fenceList;
    }

    /*
    从 目录中 或者 组 中
     */
    public List<Fence> getFenceListByOrgIdOrCataId(String id) {
        List<Fence> fenceList = null;
        String org = JedisUtil6800.getString6(id);
        if (org == null) {
            fenceList = getFenceListByCataId(id);
        } else {
            fenceList = getFenceListByOrgId(id);
        }
        return fenceList;
    }

    /*
    从组id 那里得到FenceList
   */
    public List<Fence> getFenceListByOrgId(String orgId) {
        Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId), Organization.class);
        List<Fence> fenceList = new ArrayList<>();
        JSONArray fences = JSONArray.parseArray(organization.getFence_ids());
        System.out.println("此 组 下所绑定的围栏 : " + fences);
        for (Object fencdId : fences) {
            Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(fencdId + ""), Fence.class);
            fenceList.add(fence);
        }
        return fenceList;
    }

    /*
    从目录id 那里得到FenceList
  */
    public List<Fence> getFenceListByCataId(String cataId) {
        Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId), Catalog.class);
        List<Fence> fenceList = new ArrayList<>();
        JSONArray fences = JSONArray.parseArray(catalog.getFence_ids());
        System.out.println("此 目录 下所绑定的围栏 : " + fences);
        for (Object fencdId : fences) {
            Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(fencdId + ""), Fence.class);
            fenceList.add(fence);
        }
        return fenceList;
    }

    /*
    /fence/GroupFenceList
     */
    public Groups getGroupFenceList(Map<String, Object> map) {
        String IsBind = (String) map.get("IsBind");
        String FenceId = (String) map.get("FenceId");
        String OrgId = (String) map.get("OrgId");// 可能是userId  组Id  目录Id
        String userId = (String) map.get("userId");
        String key = (String) map.get("Key");
        List<Group> groupList = null;
        if (key != null) {//传送的是  key: 组号 /目录号
            return getGroupsByKey(key, userId, OrgId, FenceId);
        }

        groupList = getGroupListByUserIdOrOrgIdOrCataId(map);

        int length = groupList.size();// 16
        System.out.println("length:" + length);
        int start = 0;
        int page = 1;
        start = Integer.parseInt(""+map.get("start"));//15
        page = Integer.parseInt(""+ map.get("page"));//2
        int end = page * UserSetting.LIMIT;//30
//        end = end > length ? length - 1 : end;
        //todo
        end = end > length || end == length ? length - 1 : end;
        System.out.println("start:" + start + "  end:" + (end + 1));
        groupList = groupList.subList(start, end + 1);
        Groups groups = new Groups();
        groups.setData(groupList);
        groups.setExcel(false);
        groups.setLimit(UserSetting.LIMIT);
        groups.setPage(page);
        groups.setTotal(groupList.size());
        return (groups);
    }

    /*
    key  可能是 组号 或者 目录号
     */
    public Groups getGroupsByKey(String key, String userId, String OrgId, String fenceId) {
        List<Group> groupList = new ArrayList<>();
        Group group = new Group();
        Organization organization = null;
        Catalog catalog = null;
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        System.out.println(userOrg.toString());

        if (UserSetting.ADMINID.equals(userOrg.getUserName())) {// admin 搜索所有都有
            String strOrg = JedisUtil6800.getString6(key);
            String strCata = JedisUtil6800.getString7(key);
            if (strOrg != null) {
                organization = JSONObject.parseObject(strOrg, Organization.class);
                System.out.println(organization);
            } else if (strCata != null) {
                catalog = JSONObject.parseObject(strCata, Catalog.class);
                System.out.println(catalog);
            } else {
                return null;
            }

            if (organization != null) {
                System.out.println("admin搜索组:" + key);
                UserOrg userOrg1 = JSONObject.parseObject(JedisUtil6800.getString9(organization.getParent_id()), UserOrg.class);
                group.setOrgId(key);
                group.setParentName(userOrg1.getUserName());
                group.setOrgName(organization.getOrganization_name());
                group.setFenceAlarmTypeText(organization.getGroup_fence_alarm_type());
                JSONArray orgGroupFenceIds = JSON.parseArray(organization.getGroup_fence_ids());
                int index = commonService.isContains(orgGroupFenceIds, fenceId);
                if (index != -1) {
                    String groupFenceAlarmTypeText = JSON.parseArray(organization.getGroup_fence_alarm_type()).getString(index);
                    String groupBindTime = JSON.parseArray(organization.getGroup_bind_time()).getString(index);
                    group.setFenceAlarmTypeText(getFenceAlarmTextByType(groupFenceAlarmTypeText));
                    group.setBindTime(groupBindTime);
                } else {
                    group.setFenceAlarmTypeText("此组未关联此围栏");
                    group.setBindTime(null);
                }
                groupList.add(group);
            } else if (catalog != null) {
                System.out.println("admin搜索目录:" + key);
                Organization org = JSONObject.parseObject(JedisUtil6800.getString6(catalog.getParent_id()), Organization.class);
                group.setParentName(org.getOrganization_name());
                group.setOrgId(key);
                group.setOrgName(catalog.getCatalog_name());

                JSONArray cataGroupFenceIds = JSON.parseArray(catalog.getGroup_fence_ids());
                int index = commonService.isContains(cataGroupFenceIds, fenceId);
                if (index != -1) {
                    String groupFenceAlarmTypeText = JSON.parseArray(catalog.getGroup_fence_alarm_type()).getString(index);
                    String groupBindTime = JSON.parseArray(catalog.getGroup_bind_time()).getString(index);
                    group.setFenceAlarmTypeText(getFenceAlarmTextByType(groupFenceAlarmTypeText));
                    group.setBindTime(groupBindTime);
                } else {
                    group.setFenceAlarmTypeText("此组未关联此围栏");
                    group.setBindTime(null);
                }
                groupList.add(group);
            }
        } else {// 普通用户搜索 ： 只有目录 id
            JSONArray catas = JSONArray.parseArray(userOrg.getCatalogueIds());
            for (Object cataId : catas) {
                if (key.equals(cataId.toString())) {
                    catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);
                    break;
                }
            }
            if (catalog != null) {
                System.out.println("普通用户搜索目录:" + key + "   " + catalog);
                Organization org = JSONObject.parseObject(JedisUtil6800.getString6(catalog.getParent_id()), Organization.class);
                group.setParentName(org.getOrganization_name());
                group.setOrgId(key);
                group.setOrgName(catalog.getCatalog_name());
                JSONArray cataGroupFenceIds = JSON.parseArray(catalog.getGroup_fence_ids());
                int index = commonService.isContains(cataGroupFenceIds, fenceId);
                if (index != -1) {
                    String groupFenceAlarmTypeText = JSON.parseArray(catalog.getGroup_fence_alarm_type()).getString(index);
                    String groupBindTime = JSON.parseArray(catalog.getGroup_bind_time()).getString(index);
                    group.setFenceAlarmTypeText(getFenceAlarmTextByType(groupFenceAlarmTypeText));
                    group.setBindTime(groupBindTime);
                } else {
                    group.setFenceAlarmTypeText("此组未关联此围栏");
                    group.setBindTime(null);
                }
                groupList.add(group);
            } else {
                return null;
            }
        }

        Groups groups = new Groups();
        groups.setData(groupList);
        groups.setExcel(false);
        groups.setLimit(UserSetting.LIMIT);
        groups.setPage(1);
        groups.setTotal(groupList.size());
        return groups;
    }

    /*
    admin 和 普通用户  默认显示数据
    /fence/GroupFenceList
     */
    public List<Group> getGroupListByUserIdOrOrgIdOrCataId(Map<String, Object> map) {
        String IsBind = (String) map.get("IsBind");
        String userId = (String) map.get("userId");//1  admin
        String OrgId = (String) map.get("OrgId");// 可能是userId=1-100  组Id  目录Id
        String FenceId = (String) map.get("FenceId");
        List<Group> bindGroups = new ArrayList<>();
        List<Group> unBindGroups = new ArrayList<>();

        if (OrgId == null || "".equals(OrgId)) {
            if (!UserSetting.ADMINID.equals(userId)) {//不是管理员登录 只是一般用户登录（还是可能是新用户）
                JSONArray orgs = JSONArray.parseArray(JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class).getOrgnizationIds());
                if (orgs.size() > 0) {
                    String orgId = orgs.getString(0);//(默认第一组)
                    Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId), Organization.class);
                    JSONArray cataArray = JSONArray.parseArray(organization.getCatalogue_ids());
                    for (Object cataId : cataArray) {
                        Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);
                        Group group = new Group();
                        group.setOrgId(catalog.getCatalog_id());
                        group.setParentName(organization.getOrganization_name());
                        group.setOrgName(catalog.getCatalog_name());
                        group.setFenceId(FenceId);
                        JSONArray cataGroupFenceIds = JSON.parseArray(catalog.getGroup_fence_ids());
                        int index = commonService.isContains(cataGroupFenceIds, FenceId);
                        if (index != -1) {  //有关联
                            String groupFenceAlarmTypeText = JSON.parseArray(catalog.getGroup_fence_alarm_type()).getString(index);
                            String groupBindTime = JSON.parseArray(catalog.getGroup_bind_time()).getString(index);
                            group.setFenceAlarmTypeText(getFenceAlarmTextByType(groupFenceAlarmTypeText));
                            group.setBindTime(groupBindTime);
                            bindGroups.add(group);
                        } else {//无关联
                            group.setFenceAlarmTypeText("此组未关联此围栏");
                            group.setBindTime(null);
                            unBindGroups.add(group);
                        }
                    }
                    if (UserSetting.DEFAULT.equals(IsBind)) {
                        bindGroups.addAll(unBindGroups);
                        return bindGroups;
                    } else if (UserSetting.ISBIIND.equals(IsBind)) {
                        return bindGroups;
                    } else if (UserSetting.UNBIIND.equals(IsBind)) {
                        return unBindGroups;
                    }
                } else {
                    System.out.println("此用户 " + userId + " 未绑定相关组！");
                }
            } else {//管理员登录  子一级是 普通用户
                System.out.println("admin 登陆");
                List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);//找到所有的普通用户
                for (UserOrg userOrg : userOrgList) {//初始是 100个用户 100*4=400个组
                    if (userOrg.getUserName().equals(UserSetting.ADMINID) || userOrg.getUserName().equals(UserSetting.QIANXI)) {
                        continue;
                    }
                    JSONArray orgArray = JSONArray.parseArray(userOrg.getOrgnizationIds());// 有可能是 新建用户 所以组 目录 等等 都是null
                    if (orgArray.size() != 0) {
                        for (Object orgId : orgArray) {//100*4=400个组
                            Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);
                            Group group = new Group();
                            group.setParentName(userOrg.getUserName());
                            group.setOrgId(organization.getOrganization_id());
                            group.setOrgName(organization.getOrganization_name());
                            group.setFenceId(FenceId);

                            JSONArray cataGroupFenceIds = JSON.parseArray(organization.getGroup_fence_ids());
                            int index = commonService.isContains(cataGroupFenceIds, FenceId);
                            if (index != -1) {
                                String groupFenceAlarmTypeText = JSON.parseArray(organization.getGroup_fence_alarm_type()).getString(index);
                                String groupBindTime = JSON.parseArray(organization.getGroup_bind_time()).getString(index);
                                group.setFenceAlarmTypeText(getFenceAlarmTextByType(groupFenceAlarmTypeText));
                                group.setBindTime(groupBindTime);
                                bindGroups.add(group);
                            } else {
                                group.setFenceAlarmTypeText("此组未关联此围栏");
                                group.setBindTime(null);
                                unBindGroups.add(group);
                            }
                        }
                        if (UserSetting.DEFAULT.equals(IsBind)) {
                            bindGroups.addAll(unBindGroups);
                            return bindGroups;
                        } else if (UserSetting.ISBIIND.equals(IsBind)) {
                            return bindGroups;
                        } else if (UserSetting.UNBIIND.equals(IsBind)) {
                            return unBindGroups;
                        }
                    } else {
                        System.out.println("此用户 " + userId + " 未绑定相关组 ! 无法进行绑定 ！");
                    }
                }
            }
        } else {
            System.out.println("Org 不为空：" + OrgId);// 到了这里就是具体点击效果了  可能是普通用户 userid  组id  目录id
            bindGroups = getGroupListByAllId(userId, OrgId, FenceId, IsBind);
            //                                                             带着userId 组Id, 目录Id
        }
        return bindGroups;
    }

    /*
    /fence/GroupFenceList
     */
    public List<Group> getGroupListByAllId(String userId, String id, String FenceId, String IsBind) {
        List<Group> bindGroups = new ArrayList<>();
        List<Group> unBindGroups = new ArrayList<>();
        String strUser = JedisUtil6800.getString9(id);//点击用户
        String strOrg = JedisUtil6800.getString6(id);//点击组
        String strCata = JedisUtil6800.getString7(id);//点击目录
        if (strUser != null) {//admin 登录点击 用户* 发送到后台
            UserOrg userOrg = JSONObject.parseObject(strUser, UserOrg.class);//  普通用户
            JSONArray orgArray = JSONArray.parseArray(userOrg.getOrgnizationIds());//防止为空
            if (orgArray.size() > 0) {
                for (Object orgId : orgArray) {
                    Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);
                    Group group = new Group();
                    group.setParentName(userOrg.getUserName());
                    group.setOrgId(organization.getOrganization_id());
                    group.setOrgName(organization.getOrganization_name());
                    group.setFenceId(FenceId);

                    JSONArray cataGroupFenceIds = JSON.parseArray(organization.getGroup_fence_ids());
                    int index = commonService.isContains(cataGroupFenceIds, FenceId);
                    if (index != -1) {
                        String groupFenceAlarmTypeText = JSON.parseArray(organization.getGroup_fence_alarm_type()).getString(index);
                        String groupBindTime = JSON.parseArray(organization.getGroup_bind_time()).getString(index);
                        group.setFenceAlarmTypeText(getFenceAlarmTextByType(groupFenceAlarmTypeText));
                        group.setBindTime(groupBindTime);
                        bindGroups.add(group);
                    } else {
                        group.setFenceAlarmTypeText("此组未关联此围栏");
                        group.setBindTime(null);
                        unBindGroups.add(group);
                    }
                }
                if (UserSetting.DEFAULT.equals(IsBind)) {
                    bindGroups.addAll(unBindGroups);
                    return bindGroups;
                } else if (UserSetting.ISBIIND.equals(IsBind)) {
                    return bindGroups;
                } else if (UserSetting.UNBIIND.equals(IsBind)) {
                    return unBindGroups;
                }
            }
        } else if (strOrg != null) {
            Organization organization = JSONObject.parseObject(strOrg, Organization.class);
            JSONArray cataArray = JSONArray.parseArray(organization.getCatalogue_ids());//防止为空
            if (cataArray.size() > 0) {
                for (Object cataId : cataArray) {
                    Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);
                    Group group = new Group();
                    group.setParentName(organization.getOrganization_name());
                    group.setOrgId(catalog.getCatalog_id());
                    group.setOrgName(catalog.getCatalog_name());
                    group.setFenceId(FenceId);

                    JSONArray cataGroupFenceIds = JSON.parseArray(catalog.getGroup_fence_ids());
                    int index = commonService.isContains(cataGroupFenceIds, FenceId);
                    if (index != -1) {
                        String groupFenceAlarmTypeText = JSON.parseArray(catalog.getGroup_fence_alarm_type()).getString(index);
                        String groupBindTime = JSON.parseArray(catalog.getGroup_bind_time()).getString(index);
                        group.setFenceAlarmTypeText(getFenceAlarmTextByType(groupFenceAlarmTypeText));
                        group.setBindTime(groupBindTime);
                        bindGroups.add(group);
                    } else {
                        group.setFenceAlarmTypeText("此组未关联此围栏");
                        group.setBindTime(null);
                        unBindGroups.add(group);
                    }
                }
                if (UserSetting.DEFAULT.equals(IsBind)) {
                    bindGroups.addAll(unBindGroups);
                    return bindGroups;
                } else if (UserSetting.ISBIIND.equals(IsBind)) {
                    return bindGroups;
                } else if (UserSetting.UNBIIND.equals(IsBind)) {
                    return unBindGroups;
                }
            }
        } else if (strCata != null) {
            Catalog catalog = JSONObject.parseObject(strCata, Catalog.class);
            String parentId = catalog.getParent_id();
            Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(parentId), Organization.class);
            Group group = new Group();
            group.setParentName(organization.getOrganization_name());
            group.setOrgId(catalog.getCatalog_id());
            group.setOrgName(catalog.getCatalog_name());
            group.setFenceId(FenceId);

            JSONArray cataGroupFenceIds = JSON.parseArray(catalog.getGroup_fence_ids());
            int index = commonService.isContains(cataGroupFenceIds, FenceId);
            if (index != -1) {
                String groupFenceAlarmTypeText = JSON.parseArray(catalog.getGroup_fence_alarm_type()).getString(index);
                String groupBindTime = JSON.parseArray(catalog.getGroup_bind_time()).getString(index);
                group.setFenceAlarmTypeText(getFenceAlarmTextByType(groupFenceAlarmTypeText));
                group.setBindTime(groupBindTime);
            } else {
                group.setFenceAlarmTypeText("此组未关联此围栏");
                group.setBindTime(null);
            }
            bindGroups.add(group);
        }
        return bindGroups;
    }


    /*
     找 属于自己的 设备
     */
    public AndroidMonitorTip getAndroidMonitorTipById(Map<String, Object> map) {
        String orgId = (String) map.get("OrgId");
        String Key = (String) map.get("Key");
        List<AndroidMonitorTip> androidMonitorTips = commonService.getAndroidMonitorTipsByUserIdOrOrgIdOrCataId(orgId);//用户组所有设备
        for (AndroidMonitorTip androidMonitorTip1 : androidMonitorTips) {
            if (Key.equals(androidMonitorTip1.getDev_number())) {
                return androidMonitorTip1;
            }
        }
        return null;
    }

    /*
    搜索
    目前是 一组92个设备 46个激活 46个未激活  28个激活并绑定  18个激活未绑定
      */
    public List<AndroidMonitorTip> getAndroidMonitorTipByMap(Map<String, Object> map) {
        String orgId = (String) map.get("OrgId");
        String fenceId = (String) map.get("FenceId");
        String isBind = (String) map.get("IsBind");
        System.out.println("IsBind  " + isBind);
        List<AndroidMonitorTip> androidMonitorTipList = new ArrayList<>();
        List<AndroidMonitorTip> androidMonitorTips = commonService.getAndroidMonitorTipsByUserIdOrOrgIdOrCataId(orgId);//用户 组  所有设备
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTips) {
            if (!androidMonitorTip.isActivation()) {//未激活 绕道走
                continue;
            }
            if (UserSetting.DEFAULT.equals(isBind) || isBind == null) {//所有设备
                if (androidMonitorTip.isBind_fence()) {//用于显示 绑定时间
                    JSONArray fence_ids = JSONArray.parseArray(androidMonitorTip.getFence_id());
                    int index = commonService.isContains(fence_ids, fenceId);
                    if (index != -1) {
                        androidMonitorTip.setBind_time(JSONArray.parseArray(androidMonitorTip.getBind_time()).get(index) + "");
                    } else {
                        androidMonitorTip.setBind_time(null);
                    }
                }
                androidMonitorTipList.add(androidMonitorTip);// 所有激活设备
            } else if (UserSetting.ISBIIND.equals(isBind)) {//已绑定
                if (androidMonitorTip.isBind_fence()) {
                    JSONArray fence_ids = JSONArray.parseArray(androidMonitorTip.getFence_id());
//                    System.out.println("围栏 id  " + fenceId + "     设备绑定  " + jsonArray);
                    int index2 = commonService.isContains(fence_ids, fenceId);
                    if (index2 != -1) {
                        androidMonitorTip.setBind_time(JSONArray.parseArray(androidMonitorTip.getBind_time()).get(index2) + "");
                        androidMonitorTipList.add(androidMonitorTip);
                        System.out.println("匹配添加围栏设备");
                    }
                }
            } else if (UserSetting.UNBIIND.equals(isBind)) {// 这次对这个 fenceId 未绑定
                if (!androidMonitorTip.isBind_fence()) {
                    androidMonitorTip.setBind_time(null);
                    androidMonitorTipList.add(androidMonitorTip);
                }
            }
        }
        return androidMonitorTipList;
    }


    /*
    /Fence/Add
     * FenceData: "118.143883|39.765006,2174"
     * FenceType: "1"
     * FenceName: "测试"
     已完成
     */
    public boolean getAddFenceResultByAdd(Map<String, Object> map) {
        String FenceData = (String) map.get("FenceData");
        String FenceType = (String) map.get("FenceType");
        String FenceName = (String) map.get("FenceName");
        String userId = (String) map.get("userId");
        String fenceId = KeyUtil.genUniqueKey();

        List<String> stringList = getFenceNameByFenceType(FenceType, FenceData);
        String fenceTypeText = stringList.get(0);
        FenceData = stringList.get(1);
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        JSONArray fenceArray = JSONArray.parseArray(userOrg.getFence_ids());
        fenceArray.add(fenceId);
        userOrg.setFence_ids(JSON.toJSONString(fenceArray));
        JedisUtil6800.setString9(userId, JSON.toJSONString(userOrg));

        Fence fence = new Fence();
        fence.setUserId((String) map.get("userId"));
        fence.setId(fenceId);
        fence.setFenceTypeText(fenceTypeText);
        fence.setEnabled(false);
        fence.setFenceType(Integer.parseInt(FenceType));
        fence.setFenceName(FenceName);
        fence.setFenceData(FenceData);
        fence.setCreatTime(TimeUtils.getGlnz());//新加代码
        JedisUtil6800.setString8(fenceId, JSONObject.toJSONString(fence));
        return true;
    }

    /*
    FenceName: "兴文县城2"
    Id: "10"
    已完成
  */
    public boolean getUpdateFenceResultByUpdate(Map<String, Object> map) {
        String FenceName = (String) map.get("FenceName");
        String Id = (String) map.get("Id");
        Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(Id), Fence.class);
        if (fence != null) {
            System.out.println(fence.toString());
            fence.setFenceName(FenceName);
            JedisUtil6800.setString8(Id, JSON.toJSONString(fence));
            return true;
        } else {
            return false;
        }
    }

    /*
    FenceName: "兴文县城2"
    Id: "10"  稍后设置
    围栏list
    */
    public boolean getRemoveFenceResultByRemove(Map<String, Object> map, List<String> list) {
        String userId = (String) map.get("userId");
        for (String FenceId : list) {//围栏List
            Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(FenceId + ""), Fence.class);//围栏8库
            System.out.println(fence);

            UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(fence.getUserId()), UserOrg.class);//只绑定一个用户
            JSONArray userFences = JSONArray.parseArray(userOrg.getFence_ids());
            int userFenceIndex = commonService.isContains(userFences, FenceId);
            if (userFenceIndex != -1) {//如果用户中存在此围栏 删除（保险点）
                userFences.remove(userFenceIndex);
            }
            userOrg.setFence_ids(JSON.toJSONString(userFences));
            JedisUtil6800.setString9(userOrg.getUser_id(), JSON.toJSONString(userOrg));//用户9库

            JSONArray catas = JSONArray.parseArray(fence.getCatalogueIds());//绑定的多个目录
            if (catas != null) {
                for (Object cataId : catas) {
                    Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);
                    JSONArray devFences = JSONArray.parseArray(catalog.getFence_ids());//绑定多个 “设备” 级别围栏
                    int devFenceIndex = commonService.isContains(devFences, FenceId);
                    if (devFenceIndex != -1) {
                        devFences.remove(devFenceIndex);//存在“设备” 级别的围栏 直删除即可
                    }

                    JSONArray groupFenceIds = JSONArray.parseArray(catalog.getGroup_fence_ids());
                    JSONArray groupBindTimes = JSONArray.parseArray(catalog.getGroup_bind_time());
                    JSONArray groupFenceTypes = JSONArray.parseArray(catalog.getGroup_fence_alarm_type());
                    System.out.println("目录删除此围栏前  " + groupFenceIds);

                    int groupFencesIndex = commonService.isContains(groupFenceIds, FenceId);
                    if (groupFencesIndex != -1) {// “组” 级别围栏 需要同时删除3个联动
                        groupFenceIds.remove(groupFencesIndex);
                        groupBindTimes.remove(groupFencesIndex);
                        groupFenceTypes.remove(groupFencesIndex);
                    }
                    System.out.println("目录删除此围栏后  " + groupFenceIds);
                    JSONArray devs = JSONArray.parseArray(catalog.getDevice_ids());//解绑定多个设备
                    if (devs.size() != 0) {
                        for (Object devId : devs) {
                            setUnBindFenceIdToDev(FenceId, devId + "");
                        }
                    }

                    catalog.setFence_ids(JSON.toJSONString(devFences));
                    catalog.setGroup_bind_time(JSON.toJSONString(groupBindTimes));
                    catalog.setGroup_fence_ids(JSON.toJSONString(groupFenceIds));//已经放到库中
                    catalog.setGroup_fence_alarm_type(JSON.toJSONString(groupFenceTypes));
                    JedisUtil6800.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));
                }
            }


            JSONArray orgs = JSONArray.parseArray(fence.getOrganizationIds());
            if (orgs != null) {
                for (Object orgId : orgs) {
                    Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);
                    JSONArray orgDevFences = JSONArray.parseArray(organization.getFence_ids());//包含下级 目录 设备 的“设备”级别 并集围栏

                    int orgFenceIndex = commonService.isContains(orgDevFences, FenceId);//设备级别围栏删除
                    if (orgFenceIndex != -1) {
                        orgDevFences.remove(orgFenceIndex);
                    }

                    //3个联动  id bindTime type  都是 在list 中 各个索引对应！！！
                    JSONArray groupFenceIds = JSONArray.parseArray(organization.getGroup_fence_ids());//组级别围栏删除
                    JSONArray groupBindTimes = JSONArray.parseArray(organization.getGroup_bind_time());
                    JSONArray groupFenceTypes = JSONArray.parseArray(organization.getGroup_fence_alarm_type());

                    System.out.println("组 删除此围栏前  " + groupFenceIds);
                    int index = commonService.isContains(groupFenceIds, FenceId);
                    if (index != -1) {// 说明 已经关联了 需要解绑 此围栏
                        System.out.println("组  " + organization.getOrganization_name() + "  需要解绑此围栏" + FenceId);
                        groupFenceIds.remove(index);
                        groupBindTimes.remove(index);
                        groupFenceTypes.remove(index);
                    } else {//说明未关联  不用解绑
                        System.out.println("未关联  不用解绑");
                    }
                    System.out.println("组 删除此围栏后  " + groupFenceIds);

                    organization.setGroup_fence_alarm_type(groupFenceTypes.toJSONString());
                    organization.setGroup_fence_ids(groupFenceIds.toJSONString());
                    organization.setGroup_bind_time(groupBindTimes.toJSONString());
                    JedisUtil6800.setString6(organization.getOrganization_id(), JSONObject.toJSONString(organization));
                }
            }

            JedisUtil6800.delStringByKeyAndDbNum(FenceId, 8);//删除围栏
        }
        return true;
    }

    /*
     /fence/BindFence  {"FenceId":"1583500780272918471","FenceAlarmTypeText":"1","Ids":["361054524214541","257425461946757"]}
     前端展示的都是 激活的设备
     关联设备：绑设备
     */
    public boolean setBindFenceByFenceId(JSONObject object) {
        String FenceId = object.getString("FenceId");
        String FenceAlarmType = object.getString("FenceAlarmTypeText");
        JSONArray array = JSONArray.parseArray(object.getString("Ids"));
        System.out.println("围栏ID " + FenceId + "   围栏Type  " + FenceAlarmType + "    设备号 " + array);
        for (Object devId : array) {
            setBindFenceIdToUserAndOrgAndCata(FenceId, FenceAlarmType, devId + "");
        }
        return true;
    }

    /*
    关联设备：绑设备
     */
    public void setBindFenceIdToUserAndOrgAndCata(String FenceId, String FenceAlarmType, String devId) {
        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(devId + ""), AndroidMonitorTip.class);
//        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6500.getString4(devId + ""), AndroidMonitorTip.class);
//        String FenceAlarmTypeText = androidMonitorTip.getFence_alarm_type();//围栏对应 报警类型
//        JSONArray bind_timeList = JSONArray.parseArray(androidMonitorTip.getBind_time());//围栏绑定设备时间 , 库中初始化 都是空list , 否则直接加，
//        if (bind_timeList == null) {
//            bind_timeList = new JSONArray();
//        }
//        if (UserSetting.DEFAULT.equals(FenceAlarmTypeText)) {//库中是0表示无关联  这次是 属于 第一次绑定
//            System.out.println("属于 第一次绑定围栏，绑定的设备是否是激活的 ： " + androidMonitorTip.isActivation());
//            androidMonitorTip.setEfence_support(true);//支持围栏
//            androidMonitorTip.setBind_fence(true);//绑定围栏
//            androidMonitorTip.setFence_alarm_type(FenceAlarmType);// 从0 ->1  直接覆盖
//        } else {
//            System.out.println("多次添加围栏");
//            FenceAlarmTypeText = FenceAlarmTypeText + "," + FenceAlarmType;  // 从1  ->   1,1  各自对应围栏id
//            androidMonitorTip.setFence_alarm_type(FenceAlarmTypeText);
//        }
//
//        bind_timeList.add(TimeUtils.getSdf());
//        androidMonitorTip.setBind_time(JSON.toJSONString(bind_timeList));
//
//        JSONArray devFences = JSONArray.parseArray(androidMonitorTip.getFence_id());//库中查出 fence_ids 是空list
//        if (commonService.isContains(devFences, FenceId) == -1) {//不存在 加进去
//            devFences.add(FenceId);
//        }
//        androidMonitorTip.setFence_id(JSON.toJSONString(devFences));
//        JedisUtil6500.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//绑定入库

        setBindGroupFenceByDevs(devId, FenceId, FenceAlarmType);


        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(androidMonitorTip.getUser_id()), UserOrg.class);
        JSONArray userFences = JSONArray.parseArray(userOrg.getFence_ids());
        if (commonService.isContains(userFences, FenceId) == -1) {
            userFences.add(FenceId);//用户加了一个  不代表底下的所有设备都有这个围栏，这个只是初始界面展示用户一共有几个围栏
        }
        userOrg.setFence_ids(JSON.toJSONString(userFences));
        JedisUtil6800.setString9(userOrg.getUser_id(), JSON.toJSONString(userOrg));

        Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(androidMonitorTip.getGroup_id()), Organization.class);
        JSONArray orgFences = JSONArray.parseArray(organization.getFence_ids());
        if (commonService.isContains(orgFences, FenceId) == -1) {
            orgFences.add(FenceId);//为了方便 之后 查看组 有多少个围栏
        }
        organization.setFence_ids(JSON.toJSONString(orgFences));
        JedisUtil6800.setString6(organization.getOrganization_id(), JSON.toJSONString(organization));

        Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(androidMonitorTip.getCatalogue_id()), Catalog.class);
        JSONArray cataFences = JSONArray.parseArray(catalog.getFence_ids());
        if (commonService.isContains(cataFences, FenceId) == -1) {
            cataFences.add(FenceId);//为了方便 之后 查看目录  有多少个围栏
        }
        catalog.setFence_ids(JSON.toJSONString(orgFences));
        JedisUtil6800.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));

        Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(FenceId), Fence.class);
        JSONArray orgs = JSONArray.parseArray(fence.getOrganizationIds());//为了查看围栏信息
        if (orgs == null) {//新建的围栏 只有用户id  没有关联的目录id 和 组id
            orgs = new JSONArray();
        }
        System.out.println("围栏bean 之前的组list  " + orgs);
        if (commonService.isContains(orgs, organization.getOrganization_id()) == -1) {
            orgs.add(organization.getOrganization_id());
            System.out.println("围栏bean之后的组list" + orgs);
        }

        JSONArray catas = JSONArray.parseArray(fence.getCatalogueIds());
        if (catas == null) {
            catas = new JSONArray();
        }
        System.out.println("围栏bean之前的目录list  " + catas);
        if (commonService.isContains(catas, catalog.getCatalog_id()) == -1) {
            catas.add(catalog.getCatalog_id());
            System.out.println("围栏bean之后的目录list  " + catas);
        }
        fence.setOrganizationIds(JSON.toJSONString(orgs));
        fence.setCatalogueIds(JSON.toJSONString(catas));
        JedisUtil6800.setString8(fence.getId(), JSON.toJSONString(fence));

    }

    /*
   只是仅仅 删除设备中所绑定的设备中3个  fenceid  fencetype bindtime  ， 组  目录  用户 不删除(万一其他设备还在绑定此围栏)
   关联设备：解绑设备
     */
    public boolean setUnBindFenceByFenceId(JSONObject object) {
        System.out.println(object);
        String FenceId = object.getString("FenceId");
        JSONArray devArray = JSONArray.parseArray(object.getString("Ids"));
        for (Object dev_id : devArray) {
            setUnBindFenceIdToDev(FenceId, dev_id + "");
        }
        return true;
    }

    /*
     只是仅仅 删除设备中所绑定的设备中3个  fenceid  fencetype bindtime  ， 组  目录  用户 删除(万一其他设备还在绑定此围栏)
     关联设备：解绑设备
     */
    public void setUnBindFenceIdToDev(String FenceId, String devId) {
        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(devId + ""), AndroidMonitorTip.class);
        if (!androidMonitorTip.isActivation()) {//未激活 绕道走
            return;
        }

        if (!androidMonitorTip.isBind_fence()) {
            return;
        }
        JSONArray devFences = JSONArray.parseArray(androidMonitorTip.getFence_id());
        JSONArray bind_time = JSONArray.parseArray(androidMonitorTip.getBind_time());
        JSONArray fence_types = JSONArray.parseArray(androidMonitorTip.getFence_alarm_type());

        System.out.println("设备解绑之前  " + devId + "  所关联的围栏list  " + devFences + "   是否关联   " + androidMonitorTip.isBind_fence());
        int index = commonService.isContains(devFences, FenceId);// 如果有 就返回索引 没有就返回-1
        if (index != -1) {//存在  减去
            System.out.println("存在此围栏 ↓ 正在解绑中...");
            boolean result = devFences.removeIf(o -> o.toString().equals(FenceId));//删除 fenceid
            fence_types.remove(index);//删除类型
            bind_time.remove(index);//删除 绑定时间
            if (result) {
                System.out.println("解除成功 ! ↓");
                if (devFences.size() == 0) {// 必须要  id--type--bind_time ,  一 一 对应
                    System.out.println("设置bind 为false");
                    androidMonitorTip.setBind_fence(false);//只有一个围栏 并且解除绑定的情况下 解除绑定状态
                    androidMonitorTip.setFence_alarm_type(JSON.toJSONString(new ArrayList<>()));//设置 无关联 空list
                    androidMonitorTip.setBind_time(JSON.toJSONString(new ArrayList<>()));
                }
            } else {
                System.out.println("删除 fenceid 解绑失败");
            }
        } else {
            System.out.println("设备  " + devId + "   不存在此围栏  解绑失败");
        }

        androidMonitorTip.setFence_id(JSON.toJSONString(devFences));
        System.out.println("设备  " + devId + "   最后的关联设备号 :  " + androidMonitorTip.getFence_id());
        JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//绑定入库
    }


    /*
      * FenceAlarmTypeText: "1"
     * Ids: ["1583500780272267472"]
     * 0: "1583500780272267472"
     * 传过来 全是目录id  或者 全是全部组id
     * FenceId: "1583500780272918471"
       BindFenceByGroup
       * 绑定就是 子级全都挂上此围栏
     */
    public boolean setBindFenceByGroup(JSONObject object) {
        String FenceId = object.getString("FenceId");
        String FenceAlarmType = object.getString("FenceAlarmTypeText");
        Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(FenceId), Fence.class);
        JSONArray fenceOrgs = JSONArray.parseArray(fence.getOrganizationIds());
        JSONArray array = JSONArray.parseArray(object.getString("Ids"));
        System.out.println("围栏ID " + FenceId + "   围栏Type  " + FenceAlarmType + "    组号 " + array);
        String temp = array.getString(0);
        String strCata = JedisUtil6800.getString7(temp);//先看是否是 目录id （机率大）
        if (strCata != null) {
            System.out.println("目录号绑定" + array);
            setBindGroupFenceByCatalogue(array, FenceId, FenceAlarmType);
        } else {
            System.out.println("组号绑定" + array);
            for (Object orgId : array) {
                Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);

                JSONArray catas = JSONArray.parseArray(organization.getCatalogue_ids());

                setBindGroupFenceByCatalogue(catas, FenceId, FenceAlarmType);// 组 ->下的 目录 都关联

                JSONArray groupBindTimes = JSONArray.parseArray(organization.getGroup_bind_time());
                JSONArray groupFenceTypes = JSONArray.parseArray(organization.getGroup_fence_alarm_type());
                JSONArray groupFenceIds = JSONArray.parseArray(organization.getGroup_fence_ids());

                int index = commonService.isContains(groupFenceIds, FenceId);
                if (index == -1) {// 说明 不存在
                    groupFenceIds.add(FenceId);
                    groupFenceTypes.add(FenceAlarmType);
                    groupBindTimes.add(TimeUtils.getSdf());
                } else {
                    System.out.println("已经存在此围栏，更新围栏操作");
                    groupFenceTypes.set(index, FenceAlarmType);
                    groupBindTimes.set(index, TimeUtils.getSdf());
                }

                int orgIndex = commonService.isContains(fenceOrgs, orgId + "");
                if (orgIndex == -1) {// 没有此组号
                    fenceOrgs.add(orgId);
                } else {
                    System.out.println("这个围栏存在此组");
                }

                organization.setGroup_bind_time(JSON.toJSONString(groupBindTimes));
                organization.setGroup_fence_ids(JSON.toJSONString(groupFenceIds));//放到库中
                organization.setGroup_fence_alarm_type(JSON.toJSONString(groupFenceTypes));

                JedisUtil6800.setString6(organization.getOrganization_id(), JSON.toJSONString(organization));
            }

            fence.setOrganizationIds(fenceOrgs.toJSONString());
            JedisUtil6800.setString8(FenceId, JSON.toJSONString(fence));//入库
        }
        return true;
    }

    /*
     关联组 ： 绑定 从目录单位下手
     */
    public void setBindGroupFenceByCatalogue(JSONArray cataIds, String FenceId, String FenceAlarmType) {
        Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(FenceId), Fence.class);
        JSONArray fenceCatas = JSONArray.parseArray(fence.getCatalogueIds());

        for (Object cataId : cataIds) {
            Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);//目录绑定

            JSONArray groupBindTimes = JSONArray.parseArray(catalog.getGroup_bind_time());
            JSONArray groupFenceTypes = JSONArray.parseArray(catalog.getGroup_fence_alarm_type());
            JSONArray groupFenceIds = JSONArray.parseArray(catalog.getGroup_fence_ids());

            int index = commonService.isContains(groupFenceIds, FenceId);
            if (index == -1) {// 说明不存在  添加
                System.out.println("属于不存在绑定围栏，绑定之前的围栏ids ： " + groupFenceIds);
                groupFenceIds.add(FenceId);
                groupFenceTypes.add(FenceAlarmType);
                groupBindTimes.add(TimeUtils.getSdf());
            } else {// 说明存在
                System.out.println("此目录 已经存在此围栏，更新围栏操作");
                groupFenceTypes.set(index, FenceAlarmType);
                groupBindTimes.set(index, TimeUtils.getSdf());
            }

            catalog.setGroup_bind_time(JSON.toJSONString(groupBindTimes));
            catalog.setGroup_fence_ids(JSON.toJSONString(groupFenceIds));//已经放到库中
            catalog.setGroup_fence_alarm_type(JSON.toJSONString(groupFenceTypes));
            JedisUtil6800.setString7(catalog.getCatalog_id(), JSON.toJSONString(catalog));

            int cataIndex = commonService.isContains(fenceCatas, cataId + "");
            if (cataIndex == -1) {// 没有此目录号
                fenceCatas.add(cataId);//添加到这个围栏 所绑定的目录list 中
            } else {
                System.out.println("存在此目录");
            }

            JSONArray devs = JSONArray.parseArray(catalog.getDevice_ids());
            for (Object devId : devs) {
                setBindGroupFenceByDevs(devId + "", FenceId, FenceAlarmType);//设备各个绑定此围栏
            }
        }

        fence.setCatalogueIds(fenceCatas.toJSONString());
        JedisUtil6800.setString8(FenceId, JSON.toJSONString(fence));
    }

    /*
    关联组 : 绑定 从设备下手
     */
    public void setBindGroupFenceByDevs(String devId, String FenceId, String FenceAlarmType) {
        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(devId + ""), AndroidMonitorTip.class);
        if (!androidMonitorTip.isActivation()) {//未激活 绕道走
            return;
        }

        JSONArray devFences = JSONArray.parseArray(androidMonitorTip.getFence_id());//库中查出 fence_ids 是空list
        JSONArray devFenveTypes = JSONArray.parseArray(androidMonitorTip.getFence_alarm_type());
        JSONArray bind_timeList = JSONArray.parseArray(androidMonitorTip.getBind_time());//设备围栏绑定设备时间 , 库中初始化 都是空list , 否则直接加，

        if (devFences == null) {
            devFences = new JSONArray();
        }
        if (devFenveTypes == null) {
            devFenveTypes = new JSONArray();
        }
        if (bind_timeList == null) {
            bind_timeList = new JSONArray();
        }

        if (devFenveTypes.size() == 0) {//库中是0 表示无关联  这次是 属于 第一次绑定
            System.out.println("设备 属于 第一次绑定围栏，绑定的设备是否是激活的 ： " + androidMonitorTip.isActivation());
            androidMonitorTip.setEfence_support(true);//支持围栏
            androidMonitorTip.setBind_fence(true);//绑定围栏

            devFenveTypes.add(FenceAlarmType);
            androidMonitorTip.setFence_alarm_type(JSON.toJSONString(devFenveTypes));
            bind_timeList.add(TimeUtils.getSdf());
            androidMonitorTip.setBind_time(JSON.toJSONString(bind_timeList));
            devFences.add(FenceId);
            androidMonitorTip.setFence_id(JSON.toJSONString(devFences));
        } else {
            System.out.println("设备 多次添加围栏");//要么 第二次是不一样围栏id ，要么是同一个围栏id
            int index = commonService.isContains(devFences, FenceId);
            if (index == -1) {//不存在 加进去
                devFenveTypes.add(FenceAlarmType);
                androidMonitorTip.setFence_alarm_type(JSON.toJSONString(devFenveTypes));
                bind_timeList.add(TimeUtils.getSdf());
                androidMonitorTip.setBind_time(JSON.toJSONString(bind_timeList));
                devFences.add(FenceId);
                androidMonitorTip.setFence_id(JSON.toJSONString(devFences));
            } else {//同一个围栏  更新操作
                System.out.println("更新围栏绑定信息");
                devFenveTypes.set(index, FenceAlarmType);
                androidMonitorTip.setFence_alarm_type(JSON.toJSONString(devFenveTypes));
                bind_timeList.set(index, TimeUtils.getSdf());
                androidMonitorTip.setBind_time(JSON.toJSONString(bind_timeList));
            }
        }

        JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//绑定入库

    }


    /*
/fence/UnBindFenceByGroup
   *Ids: ["1583500780278930392"]
 * 0: "1583500780278930392"
 * FenceId: "1583500780278798428"

解绑Ids可能是 目录 或者 组，如果是组 就解绑组+设备+目录解绑;
如果是目录 就解绑目录+设备；规则是 这些 目录和组 必须在自己的bean中已经存在这种"组" 级别的围栏Id,然后才能逐级解绑；最后围栏也相应减去组/目录id ；
 */
    public boolean setUnBindFenceByGroup(JSONObject object) {
        String FenceId = object.getString("FenceId");
        Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(FenceId), Fence.class);
        JSONArray array = JSONArray.parseArray(object.getString("Ids"));
        System.out.println("围栏ID " + FenceId + "    要解绑的组号   " + array);
        String temp = array.getString(0);
        String strCata = JedisUtil6800.getString7(temp);//先看是否是 目录id （机率大）

        if (strCata != null) {
            setUnBindGroupFenceByCatalogues(array, FenceId, JSONArray.parseArray(fence.getCatalogueIds()));
        } else { // 组 级别 解绑
            setUnBindGroupFenceByOrgs(array, FenceId, JSONArray.parseArray(fence.getOrganizationIds()));
        }

        return true;
    }

    /*
   关联组 ： 解绑定 从组单位下手
   */
    public void setUnBindGroupFenceByOrgs(JSONArray orgs, String FenceId, JSONArray fenceOrgs) {
        Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(FenceId), Fence.class);
        for (Object orgId : orgs) {
            Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);

            JSONArray groupFenceIds = JSONArray.parseArray(organization.getGroup_fence_ids());
            JSONArray groupBindTimes = JSONArray.parseArray(organization.getGroup_bind_time());
            JSONArray groupFenceTypes = JSONArray.parseArray(organization.getGroup_fence_alarm_type());

            int index = commonService.isContains(groupFenceIds, FenceId);
            if (index != -1) {// 说明 已经关联了 需要解绑 此围栏
                System.out.println("组  " + organization.getOrganization_name() + "  需要解绑此围栏" + FenceId);
                groupFenceIds.remove(index);
                groupBindTimes.remove(index);
                groupFenceTypes.remove(index);

                JSONArray catas = JSONArray.parseArray(organization.getCatalogue_ids());
                setUnBindGroupFenceByCatalogues(catas, FenceId, JSONArray.parseArray(fence.getCatalogueIds()));
            } else {//说明未关联  不用解绑
                System.out.println("未关联  不用解绑");
            }

            organization.setGroup_fence_alarm_type(groupFenceTypes.toJSONString());
            organization.setGroup_fence_ids(groupFenceIds.toJSONString());
            organization.setGroup_bind_time(groupBindTimes.toJSONString());
            JedisUtil6800.setString6(organization.getOrganization_id(), JSONObject.toJSONString(organization));

            int fenceOrgindex = commonService.isContains(fenceOrgs, orgId.toString());
            if (fenceOrgindex != -1) {
                fenceOrgs.remove(fenceOrgindex);
            }
        }

        fence.setCatalogueIds(JSON.toJSONString(fenceOrgs));
        JedisUtil6800.setString8(FenceId, JSON.toJSONString(fence));
    }


    /*
     关联组 ： 解绑定 从目录单位下手
     */
    public void setUnBindGroupFenceByCatalogues(JSONArray catas, String FenceId, JSONArray fenceCatas) {
        Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(FenceId), Fence.class);
        for (Object cataId : catas) {
            Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(cataId + ""), Catalog.class);

            JSONArray groupFenceIds = JSONArray.parseArray(catalog.getGroup_fence_ids());
            JSONArray groupBindTimes = JSONArray.parseArray(catalog.getGroup_bind_time());
            JSONArray groupFenceTypes = JSONArray.parseArray(catalog.getGroup_fence_alarm_type());

            int index = commonService.isContains(groupFenceIds, FenceId);
            if (index != -1) {// 说明 已经关联了 需要解绑 此围栏
                System.out.println("目录  " + catalog.getCatalog_name() + "  需要解绑此围栏" + FenceId);
                groupFenceIds.remove(index);
                groupBindTimes.remove(index);
                groupFenceTypes.remove(index);

                JSONArray devs = JSONArray.parseArray(catalog.getDevice_ids());
                for (Object devId : devs) {// 并且底下 设备也都要解绑
                    setUnBindGroupFenceByDevs(devId + "", FenceId);
                }
            } else {//说明未关联  不用解绑
                System.out.println("未关联  不用解绑");
            }

            catalog.setGroup_fence_alarm_type(groupFenceTypes.toJSONString());
            catalog.setGroup_fence_ids(groupFenceIds.toJSONString());
            catalog.setGroup_bind_time(groupBindTimes.toJSONString());
            JedisUtil6800.setString7(catalog.getCatalog_id(), JSONObject.toJSONString(catalog));

            int fenceCataindex = commonService.isContains(fenceCatas, cataId + "");
            if (fenceCataindex != -1) {// 每次的目录id 是否在这个围栏中
                fenceCatas.remove(fenceCataindex);
            }
        }
        fence.setCatalogueIds(JSON.toJSONString(fenceCatas));
        JedisUtil6800.setString8(FenceId, JSON.toJSONString(fence));
    }

    /*
   关联组 ： 解绑定 从设备单位下手
   */
    public void setUnBindGroupFenceByDevs(String devId, String FenceId) {
        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(devId + ""), AndroidMonitorTip.class);
        if (!androidMonitorTip.isActivation()) {//未激活 绕道走
            return;
        }
        if (androidMonitorTip.isBind_fence()) {
            JSONArray fenceIds = JSONArray.parseArray(androidMonitorTip.getFence_id());
            JSONArray bindTime = JSONArray.parseArray(androidMonitorTip.getBind_time());
            JSONArray fenceTypes = JSONArray.parseArray(androidMonitorTip.getFence_alarm_type());
            int index = commonService.isContains(fenceIds, FenceId);
            if (index != -1) {//存在 就解绑
                fenceIds.remove(index);
                bindTime.remove(index);
                fenceTypes.remove(index);
                androidMonitorTip.setFence_id(fenceIds.toJSONString());
                androidMonitorTip.setBind_time(bindTime.toJSONString());
                androidMonitorTip.setFence_alarm_type(fenceTypes.toJSONString());
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
            } else {
                System.out.println("未绑定 此 围栏 下一个");
            }
        } else {
            System.out.println("未绑定 围栏 下一个");
        }
    }


    public static String getFenceAlarmTextByType(String FenceAlarmType) {
        String FenceAlarmTypeText = "未关联此围栏";
        switch (FenceAlarmType) {
            case "0":
                FenceAlarmTypeText = "未关联此围栏";
                break;
            case "1":
                FenceAlarmTypeText = "进围栏";
                break;
            case "2":
                FenceAlarmTypeText = "出围栏";
                break;
            case "3":
                FenceAlarmTypeText = "进出围栏";
                break;
            default:
                break;
        }
        return FenceAlarmTypeText;

    }


    public static List<String> getFenceNameByFenceType(String FenceType, String FenceData) {
        List<String> list = new ArrayList<>();
        String fenceTypeText = "圆形";
        if ("1".equals(FenceType)) {
            fenceTypeText = "圆形";
            FenceData = FenceData + ",4560.0";
        } else if ("3".equals(FenceType)) {
            fenceTypeText = "矩形";
            list.add(0, fenceTypeText);
        }

        list.add(0, fenceTypeText);
        list.add(1, FenceData);
        return list;
    }

    /**
     * fence/FencesByGroupId
     * 查找组管理中相关的围栏
     */
    public List<Fence> FencesByGroupId(Map<String, Object> map) {
        String Id = (String) map.get("Id");//组id
        Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(Id + ""), Organization.class);
        JSONArray array = JSONArray.parseArray(organization.getFence_ids());
        if (array.size() == 0 && array == null) {
            return null;
        }
        List<Fence> fenceList = new ArrayList<>(10);
        for (Object fenceId : array) {
            Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(fenceId + ""), Fence.class);
            fenceList.add(fence);
        }
        return fenceList;
    }

    /**
     * 查看终端设备相关围栏
     * FencesByTerminalId
     * http://127.0.0.1:51665/api/fence/FencesByTerminalId
     * 设备id
     * Id: "162203372877781"
     */
    public List<Fence> FencesByTerminalId(Map<String, Object> map) {
        String Id = (String) map.get("Id");//组id
        AndroidMonitorTip androidMonitorTip = JSONObject.parseObject(JedisUtil6800.getString4(Id), AndroidMonitorTip.class);
        JSONArray array = JSONArray.parseArray(androidMonitorTip.getFence_id());
        JSONArray alarmtext = JSONArray.parseArray(androidMonitorTip.getFence_alarm_type());
        if (array == null) {
            return null;
        }
        List<Fence> fenceList = new ArrayList<>(10);
        for (int i = 0; i < array.size(); i++) {
            String fenceId = array.getString(i);
            String fenceAlarmType = alarmtext.getString(i);
            Fence fence = JSONObject.parseObject(JedisUtil6800.getString8(fenceId), Fence.class);
            fence.setAlarmTypeText(getFenceAlarmTextByType(fenceAlarmType));
            fenceList.add(fence);
        }
        return fenceList;
    }
}
