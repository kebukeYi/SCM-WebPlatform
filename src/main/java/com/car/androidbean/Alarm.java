package com.car.androidbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/26  16:19
 * @Description
 */
public class Alarm {


    int alarm_type_id;//报警类型ID
    String alarm_type;//报警类型
    int alarm_num;//数量
    String send_time;//报警时间
    String user_name;//报警设备名称


    @JsonProperty("alarm_type_id")
    public int getAlarm_type_id() {
        return alarm_type_id;
    }

    public void setAlarm_type_id(int alarm_type_id) {
        this.alarm_type_id = alarm_type_id;
    }
    @JsonProperty("alarm_type")
    public String getAlarm_type() {
        return alarm_type;
    }

    public void setAlarm_type(String alarm_type) {
        this.alarm_type = alarm_type;
    }
    @JsonProperty("alarm_num")
    public int getAlarm_num() {
        return alarm_num;
    }

    public void setAlarm_num(int alarm_num) {
        this.alarm_num = alarm_num;
    }
    @JsonProperty("send_time")
    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
    @JsonProperty("user_name")
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}

