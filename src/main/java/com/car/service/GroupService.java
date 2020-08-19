package com.car.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.bean.Catalog;
import com.car.bean.Organization;
import com.car.bean.Role;
import com.car.bean.UserOrg;
import com.car.domain.Children;
import com.car.domain.GroupRole;
import com.car.domain.GroupRoles;
import com.car.redis.JedisUtil6800;
import com.car.setting.UserSetting;
import com.car.utils.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:52
 * @Description
 */
@Service
public class GroupService {

    static Random random = new Random();

    /*
  目录 分层的上一层   已更改
  */
    public List<Children> getChildenAll(String userId) {
        List<Children> childrenList = new ArrayList<>();
        if (UserSetting.ADMINID.equals(userId)) {//如果是管理员登录   admin
            List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);//查出所有用户
            for (UserOrg userOrg : userOrgList) {
                Children children = new Children();
                if (UserSetting.ADMINID.equals(userOrg.getUserName())) {//9库 也有admin
                    continue;
                }
                if (UserSetting.QIANXI.equals(userOrg.getUserName())) {//9库 迁西中关村
                    continue;
                }
                children.setParentId(UserSetting.ADMINID);
                children.setId(userOrg.getUser_id());
                children.setOrgName(userOrg.getUserName());
                childrenList.add(children);//先添加用户
                childrenList.addAll(getChildensByUserId(userOrg.getUser_id()));// 再添加 用户下的 组 和目录 一个  一个的查
            }
            return childrenList;
        } else {
            return getChildensByUserId(userId);
        }
    }


    /*
     已更改
     */
    public List<Children> getChildensByUserId(String userId) {
        List<Children> childrenList = new ArrayList<>();
        System.out.println("getChildensByUserId : " + userId);
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        JSONArray orgArray = JSONArray.parseArray(userOrg.getOrgnizationIds());
        for (Object orgId : orgArray) {
            Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);
            JSONArray cataArray = JSONArray.parseArray(organization.getCatalogue_ids());
            Children children = new Children();
            children.setId(orgId + "");
            children.setOrgName(organization.getOrganization_name());
            children.setParentId(userId);
            childrenList.add(children);
            for (Object catalogueId : cataArray) {
                Catalog catalog = JSONObject.parseObject(JedisUtil6800.getString7(catalogueId + ""), Catalog.class);
                Children childrens = new Children();
                childrens.setParentId(orgId + "");
                childrens.setId(catalogueId + "");
                childrens.setSeq(3);
                childrens.setLevel(3);
                childrens.setOrgName(catalog.getCatalog_name());
                childrenList.add(childrens);
            }
        }
        return childrenList;
    }


    /*
    SearchOrgName   已更改
     */
    public Organization getOrganizationNameByOrgId(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        JSONArray jsonArray = JSONArray.parseArray(userOrg.getOrgnizationIds());
        String orgId = jsonArray.get(0) + "";
        Organization organization = null;
        String orgStr = JedisUtil6800.getString6(orgId);
        if (orgStr != null) {
            organization = JSONObject.parseObject(orgStr, Organization.class);
        }
        return organization;
    }


    /*
    SearchCurrentGroupId
     */
    public String SearchCurrentGroupId(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        String data = "";
        if (UserSetting.ADMINID.equals(userId)) {

        } else {
            UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
            JSONArray jsonArray = JSONArray.parseArray(userOrg.getOrgnizationIds());
            data = jsonArray.getString(0);
        }
        return data;
    }

    /**
     * /api/Group/Search
     */
    public GroupRoles Search(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        GroupRoles groupRoles1 = new GroupRoles();
        List<Role> roleList = new ArrayList<>(20);
        roleList = JedisUtil6800.getRolesAll(12);
        roleList = roleList.subList(0, 1);

        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        JSONArray orgArray = JSONArray.parseArray(userOrg.getOrgnizationIds());

        List<GroupRole> groupRoles = new ArrayList<>(30);

        for (Object orgId : orgArray) {
            Organization organization = JSONObject.parseObject(JedisUtil6800.getString6(orgId + ""), Organization.class);
            GroupRole groupRole = new GroupRole();
            groupRole.setOrgName(organization.getOrganization_name());
            groupRole.setParentName(userOrg.getUserName());
            groupRole.setSeq(random.nextInt(10));
            groupRole.setId(orgId + "");
            groupRole.setCreateTime(TimeUtils.getPreTimeByDay(8, 1));
            groupRole.setRoleOrgModels(roleList);
            groupRoles.add(groupRole);
        }
        groupRoles1.setData(groupRoles);
        return groupRoles1;
    }
}
