package com.car.mysql.impl;

import com.car.bean.UserOrg;
import com.car.mapper.UserManagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:51
 * @Description
 */
@Service
public class UserManagementImpl {

    @Autowired
    UserManagementMapper userManagementMapper;


    List<UserOrg> getUserByMap(Map<String, Object> map) {

        List<UserOrg> userOrgList = userManagementMapper.getUserByMap(map);

        return userOrgList;
    }

    void insertUser(Map<String, Object> map) {
        userManagementMapper.insertUser(map);
    }

    void insertUserForEach(List<UserOrg> userOrgs) {
        userManagementMapper.insertUserForEach(userOrgs);
    }

    void delUserById(String id) {
        userManagementMapper.delUserById(id);
    }

    void updateUser(Map<String, Object> map) {
        userManagementMapper.updateUser(map);
    }

}
