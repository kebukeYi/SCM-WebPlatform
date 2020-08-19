package com.car.domain.main;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  18:07
 * @Description
 */
public class DailyAlarm {

   
    private String AlarmDate;
    int Count;

    
    @JsonProperty("AlarmDate")
    public String getAlarmDate() {
		return AlarmDate;
	}

	public void setAlarmDate(String alarmDate) {
		AlarmDate = alarmDate;
	}

	 @JsonProperty("Count")
	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public DailyAlarm( int count,String alarmDate) {
        AlarmDate = alarmDate;
        Count = count;
    }
}
