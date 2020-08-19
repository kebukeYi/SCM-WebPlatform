package com.car.mapper;

import com.car.androidbean.AndroidMonitorTip;
import com.car.domain.Offline;
import com.car.domain.OnlineReport;
import com.car.mysql.impl.OfflineReportImpl;
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
 * @Creat Time : 2020/5/13  18:54
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class OfflineReportMapperTest {
    static Random random = new Random();

    @Autowired
    OfflineReportImpl offlineReport;

    @Test
    void insertOffline() {
    }

    @Test
    void insertOfflineForEach() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        System.out.println(androidMonitorTipList.size());
        List<Offline> offlineList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            AndroidMonitorTip androidMonitorTip = androidMonitorTipList.get(i);
            if (!androidMonitorTip.isOnline()) {
                Offline report = new Offline();
                BeanUtils.copyProperties(androidMonitorTip, report);
                int days = random.nextInt(5);
                report.setOffline_second(days * 1440 + "");
                report.setModel_name(ReportService.getModelNameByModelId(androidMonitorTip.getModel_id()));
                report.setModel_type_name(ReportService.getModelTypeNameByModeType(androidMonitorTip.getModel_type()));
                offlineList.add(report);
            }
        }
        if (offlineList.size() != 0) {
            offlineReport.insertOfflineForEach(offlineList);
        }
    }
}