package com.car.mysql.impl;

import com.car.domain.OnlineReport;
import com.car.domain.OnlineReports;
import com.car.mapper.OnlineReportMapper;
import com.car.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:47
 * @Description
 */
@Service
public class OnlineReportImpl {


    private OnlineReportMapper onlineReportMapper;


    @Autowired
    private CommonService commonService;

    @Autowired
    public void OfflineReportImpl(OnlineReportMapper onlineReportMapper) {
        this.onlineReportMapper = onlineReportMapper;
    }

    public OnlineReports getOfflineListFromMap(Map<String, Object> map) {
        map = commonService.getIdByMap(map);
        List<OnlineReport> onlineReportList = onlineReportMapper.queryAll(map);
        OnlineReports onlineReports = new OnlineReports();
        onlineReports.setData(onlineReportList);
        onlineReports.setTotal(onlineReportList.size());
        return onlineReports;
    }


    public void insertOnline(OnlineReport onlineReport) {
        onlineReportMapper.insert(onlineReport);
    }

    public void insertOnlineForEach(List<OnlineReport> onlineReport) {
        onlineReportMapper.insertForeach(onlineReport);
    }
}
