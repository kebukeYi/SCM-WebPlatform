package com.car.mapper;

import com.car.androidbean.AndroidMonitorTip;
import com.car.domain.OnlineReport;
import com.car.mysql.impl.OnlineReportImpl;
import com.car.redis.JedisUtil6800;
import com.car.service.CommonService;
import com.car.service.FenceService;
import com.car.service.MonitorService;
import com.car.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/13  17:04
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class OnlineReportMapperTest {

    @Autowired
    OnlineReportImpl onlineReport;

    @Test
    void insert() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        List<OnlineReport> onlineReports = new ArrayList<>();
        int Lng = 119;
        int Lat = 39;
        for (int i = 10000; i < androidMonitorTipList.size(); i++) {
            AndroidMonitorTip androidMonitorTip = androidMonitorTipList.get(i);
            OnlineReport report = new OnlineReport();
            BeanUtils.copyProperties(androidMonitorTip, report);
            report.setModel_name(ReportService.getModelNameByModelId(androidMonitorTip.getModel_id()));
            report.setModel_type_name(ReportService.getModelTypeNameByModeType(androidMonitorTip.getModel_type()));
            report.setLng(Lng + Math.random() + "");
            report.setLat(Lat + Math.random() + "");
            report.setLnglat(report.getLng() + "," + report.getLat());
            onlineReports.add(report);
        }
        onlineReport.insertOnlineForEach(onlineReports);
    }
}