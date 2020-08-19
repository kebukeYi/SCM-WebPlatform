package com.car.mapper;

import com.car.androidbean.AndroidMonitorTip;
import com.car.domain.Alarm;
import com.car.domain.Offline;
import com.car.mysql.impl.AlarmReportImpl;
import com.car.redis.JedisUtil6800;
import com.car.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/13  22:36
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class AlarmReportMapperTest {
    @Autowired
    AlarmReportImpl alarmReport;

    static Random random = new Random();

    @Test
    void insertAlarm() {
    }

    @Test
    void insertAlarmForEach() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        System.out.println(androidMonitorTipList.size());
        List<Alarm> offlineList = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            AndroidMonitorTip androidMonitorTip = androidMonitorTipList.get(i);
            if (androidMonitorTip.isOnline()) {
                Alarm alarm = new Alarm();
                BeanUtils.copyProperties(androidMonitorTip, alarm);
                int alarm_type = androidMonitorTip.getAlarm_type_id();
                System.out.println(alarm_type);
                alarm.setAlarm_type_id(alarm_type);
                alarm.setAlarm_text(ReportService.getAlarmNameByAlarmType(alarm_type + ""));
                alarm.setContent("一般报警");
                offlineList.add(alarm);
            }
        }
        if (offlineList.size() != 0) {
            alarmReport.insertAlarmForEach(offlineList);
        }
    }
}