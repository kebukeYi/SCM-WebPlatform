package com.car.mysql.impl;

import com.car.domain.Offline;
import com.car.domain.Offlines;
import com.car.mapper.OfflineReportMapper;
import com.car.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:49
 * @Description
 */
@Service
public class OfflineReportImpl {

    @Autowired
    OfflineReportMapper offlineReportMapper;
    @Autowired
    private CommonService commonService;


    public Offlines getOfflineListFromMap(Map<String, Object> map) {
        map = commonService.getIdByMap(map);
        List<Offline> offlineList = offlineReportMapper.getOfflineListFromMap(map);
        Offlines offlines = new Offlines();
        offlines.setData(offlineList);
        return offlines;
    }

    public void insertOffline(Offline offlines) {
        offlineReportMapper.insertOffline(offlines);
    }


    public void insertOfflineForEach(List<Offline> offlines) {
        offlineReportMapper.insertOfflineForEach(offlines);
    }


}
