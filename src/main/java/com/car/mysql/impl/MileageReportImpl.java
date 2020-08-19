package com.car.mysql.impl;

import com.car.domain.Mileage;
import com.car.domain.Mileages;
import com.car.mapper.MileageReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:50
 * @Description
 */
@Service
public class MileageReportImpl {

    @Autowired
    MileageReportMapper mileageReportMapper;


    public Mileages getOfflineListFromMap(Map<String, Object> map) {
        List<Mileage> offlineList = mileageReportMapper.getMileageListFormMap(map);
        Mileages offlines = new Mileages();
        offlines.setData(offlineList);
        return offlines;
    }

    public void insertMileage(Mileage mileage) {
        mileageReportMapper.insertMileage(mileage);
    }


    public void insertMileageForEach(List<Mileage> mileage) {
        mileageReportMapper.insertMileageForEach(mileage);
    }


}
