package com.car.mapper;

import com.car.domain.Alarm;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  16:44
 * @Description
 */
//@Repository
public interface AlarmReportMapper {

    List<Alarm> getAlarmListFormMap(Map<String, Object> map);

    void insertAlarm(Alarm alarm);

    void insertAlarmForEach(List<Alarm> alarms);

}
