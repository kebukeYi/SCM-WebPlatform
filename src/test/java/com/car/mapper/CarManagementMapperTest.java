package com.car.mapper;

import com.car.androidbean.AndroidMonitorTip;
import com.car.domain.Alarm;
import com.car.domain.Car;
import com.car.mysql.impl.CarManagementImpl;
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
 * @Creat Time : 2020/5/15  10:41
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CarManagementMapperTest {

    @Autowired
    CarManagementImpl carManagement;


    static Random random = new Random();

    @Test
    void insertCar() {
    }

    @Test
    void insertCarForEach() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        System.out.println(androidMonitorTipList.size());
        List<Car> offlineList = new ArrayList<>();
        for (int i = 500; i < 5000; i++) {
            AndroidMonitorTip androidMonitorTip = androidMonitorTipList.get(i);
//            if (androidMonitorTip.isOnline()) {
            Car car = new Car();
            BeanUtils.copyProperties(androidMonitorTip, car);
            car.setBrand("三菱");
            car.setCarColor("黑色");
            car.setVIN("15-56");
            car.setCarType(ReportService.getCarCarTypeNameByCarModel(random.nextInt(10) + ""));
            car.setPhone("18732569857");
            car.setOwner("王先生");
            car.setZipDeviceModels(androidMonitorTip.getDev_number());
            car.setRemark("");
            car.setModel(ReportService.getModelNameByModelId(androidMonitorTip.getModel_id()));

            offlineList.add(car);
//            }
        }
        if (offlineList.size() != 0) {
            carManagement.insertCarForEach(offlineList);
        }
    }
}