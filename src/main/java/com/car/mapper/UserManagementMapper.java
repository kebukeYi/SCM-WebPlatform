package com.car.mapper;

import com.car.bean.UserOrg;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:12
 * @Description 暂时redis 管理
 * http://127.0.0.1:51665/api/User/Search
 */
public interface UserManagementMapper {


    List<UserOrg> getUserByMap(Map<String, Object> map);

    void insertUser(Map<String, Object> map);

    void insertUserForEach(List<UserOrg> userOrgs);

    void delUserById(String id);

    void updateUser(Map<String, Object> map);


}
