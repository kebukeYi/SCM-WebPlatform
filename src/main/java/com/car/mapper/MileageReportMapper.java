package com.car.mapper;

import com.car.domain.Alarm;
import com.car.domain.Mileage;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:11
 * @Description
 */
public interface MileageReportMapper {

    List<Mileage> getMileageListFormMap(Map<String, Object> map);

    void insertMileage(Mileage mileage);

    void insertMileageForEach(List<Mileage> mileage);
}
