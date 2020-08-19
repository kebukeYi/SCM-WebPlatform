package com.car.mapper;

import com.car.domain.Offline;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:10
 * @Description
 */
public interface OfflineReportMapper {


    List<Offline> getOfflineListFromMap(Map<String, Object> map);

    void insertOffline(Offline offline);

    void insertOfflineForEach(List<Offline> offlines);


}
