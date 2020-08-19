package com.car.mysql.impl;

import com.car.mapper.AlarmReportMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  17:54
 * @Description
 */


@RunWith(SpringRunner.class)
@SpringBootTest
class AlarmReportImplTest {


    @Autowired
    private AlarmReportImpl alarmReport;

    @Test
    void getAlarmListFromMap() {
        Map<String, String> map = new HashMap<>();
//        map.put("", "");
        map.put("OrgId", "1584671979279219847");
        map.put("start", "0");
        map.put("limit", "15");
//        System.out.println(alarmReport.getAlarmListFromMap(map));

    }
}