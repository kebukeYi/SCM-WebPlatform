package com.car.mapper;

import com.car.domain.OnlineReport;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:11
 * @Description
 */
public interface OnlineReportMapper {

    List<OnlineReport> queryAll(Map<String, Object> map);

    void insert(OnlineReport onlineReport);

    void insertForeach(List<OnlineReport> onlineReports);

    void update(OnlineReport onlineReport);

    void deleteById(int id);


}
