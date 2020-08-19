package com.car.mysql.impl;

import com.car.domain.Alarm;
import com.car.domain.Alarms;
import com.car.mapper.AlarmReportMapper;
import com.car.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  17:49
 * @Description
 */
@Service
public class AlarmReportImpl {

    @Autowired
    private AlarmReportMapper alarmReportMapper;

    @Autowired
    private CommonService commonService;


    public Alarms getAlarmListFromMap(Map<String, Object> map) {
        Alarms alarms = new Alarms();
        map = commonService.getIdByMap(map);
        List<Alarm> alarmList = alarmReportMapper.getAlarmListFormMap(map);
        alarms.setData(alarmList);
        alarms.setTotal(alarmList.size());
        alarms.setPage((Integer) map.get("page"));
        alarms.setLimit((Integer) map.get("limit"));
        return alarms;
    }


    public void insertAlarm(Alarm alarm) {
        alarmReportMapper.insertAlarm(alarm);
    }

    public void insertAlarmForEach(List<Alarm> alarms) {
        alarmReportMapper.insertAlarmForEach(alarms);
    }


}
