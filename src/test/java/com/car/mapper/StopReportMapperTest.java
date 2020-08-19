package com.car.mapper;

import com.car.androidbean.AndroidMonitorTip;
import com.car.domain.Stop;
import com.car.mysql.impl.StopReportImpl;
import com.car.redis.JedisUtil6800;
import com.car.utils.TimeUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/14  0:02
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class StopReportMapperTest {

    static Random random = new Random();

    @Autowired
    StopReportImpl stopReport;

    @Test
    void insertStop() {
    }

    @Test
    void insertStopForEach() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        System.out.println(androidMonitorTipList.size());
        List<Stop> offlineList = new ArrayList<>();
        for (int i = 15000; i < androidMonitorTipList.size(); i++) {
            AndroidMonitorTip androidMonitorTip = androidMonitorTipList.get(i);
            if (androidMonitorTip.isActivation()) {
                Stop alarm = new Stop();
                BeanUtils.copyProperties(androidMonitorTip, alarm);
                int days = random.nextInt(10);
                alarm.setStartTime(TimeUtils.getPreTimeByDay(days, 1));
                alarm.setEndTime(TimeUtils.getGlnz());
                alarm.setIntervaltext(days * 1440 + "");
                offlineList.add(alarm);
            }
        }
        if (offlineList.size() != 0) {
            stopReport.insertStopForEach(offlineList);
        }

    }
}