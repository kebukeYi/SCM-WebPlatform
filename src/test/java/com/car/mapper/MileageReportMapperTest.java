package com.car.mapper;

import com.car.androidbean.AndroidMonitorTip;
import com.car.domain.Alarm;
import com.car.domain.Mileage;
import com.car.mysql.impl.MileageReportImpl;
import com.car.redis.JedisUtil6800;
import com.car.service.ReportService;
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

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/14  0:48
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class MileageReportMapperTest {
    @Autowired
    MileageReportImpl mileageReport;
    static Random random = new Random();

    @Test
    void insertMileage() {
    }

    @Test
    void insertMileageForEach() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        List<Mileage> offlineList = new ArrayList<>();
        for (int i = 10500; i < 12000; i++) {
            AndroidMonitorTip androidMonitorTip = androidMonitorTipList.get(i);
            if (androidMonitorTip.isOnline()) {
                Mileage mileage = new Mileage();
                BeanUtils.copyProperties(androidMonitorTip, mileage);
                mileage.setMileage(random.nextInt(500) + "");
                mileage.setModel_type_name(ReportService.getModelTypeNameByModeType(androidMonitorTip.getModel_type()));
                mileage.setStroke_date(TimeUtils.getPreTimeByDay(random.nextInt(10), 1));
                offlineList.add(mileage);
            }
        }
        if (offlineList.size() != 0) {
            mileageReport.insertMileageForEach(offlineList);
        }
    }

}
