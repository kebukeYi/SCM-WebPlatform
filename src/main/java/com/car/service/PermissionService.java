package com.car.service;

import com.car.bean.Role;
import com.car.bean.Roles;
import com.car.redis.JedisUtil6800;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/9  12:48
 * @Description
 */
@Service
public class PermissionService {


    /**
     * 组管理
     */
    public List<Role> ShowRoleOrgList(Map<String, String> map) {
        Roles roles = new Roles();
        List<Role> roleList = JedisUtil6800.getRolesAll(12);
        roles.setData(roleList);
        return roleList;
    }

    /**
     * 用户管理
     */
    public List<Role> ShowRole(Map<String, String> map) {
        Roles roles = new Roles();
        List<Role> roleList = JedisUtil6800.getRolesAll(12);
        roles.setData(roleList);
        System.out.println(roleList);
        return roleList;
    }


}
