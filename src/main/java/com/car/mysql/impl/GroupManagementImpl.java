package com.car.mysql.impl;

import com.car.mapper.GroupManagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:50
 * @Description
 */
@Service
public class GroupManagementImpl {
    @Autowired
    GroupManagementMapper groupManagementMapper;
}
