package com.car.mapper;

import com.car.domain.Stop;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:11
 * @Description
 */
public interface StopReportMapper {

    List<Stop> getStopListFromMap(Map<String, Object> map);

    void insertStop(Stop stop);

    void insertStopForEach(List<Stop> stops);

}
