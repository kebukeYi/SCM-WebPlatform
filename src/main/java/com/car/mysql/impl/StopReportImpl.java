package com.car.mysql.impl;

import com.car.domain.OnlineReport;
import com.car.domain.OnlineReports;
import com.car.domain.Stop;
import com.car.domain.Stops;
import com.car.mapper.StopReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:51
 * @Description
 */
@Service
public class StopReportImpl {

    @Autowired
    StopReportMapper stopReportMapper;

    public Stops getOfflineListFromMap(Map<String, Object> map) {
        List<Stop> offlineList = stopReportMapper.getStopListFromMap(map);
        Stops offlines = new Stops();
        offlines.setData(offlineList);
        return offlines;
    }


    public void insertStop(Stop stop) {
        stopReportMapper.insertStop(stop);
    }

    public void insertStopForEach(List<Stop> stops) {
        stopReportMapper.insertStopForEach(stops);
    }
}
