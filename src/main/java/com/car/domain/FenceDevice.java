package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/14  17:58
 * @Description 点击绑定设备
 */
@Data
public class FenceDevice {

    String device_id;
    String dev_name;
    String dev_number;
    String group_name;
    String alarm_type_id;
    String fence_alarm_type;
    String fence_id;
    String fence_alarm_type_text;
    String alarm_type_text;
    String bind_time;
    //  各自对应各自的绑定时间

    @JsonProperty("BindTime")
    public String getBind_time() {
        return bind_time;
    }

    public void setBind_time(String bind_time) {
        this.bind_time = bind_time;
    }

    @JsonProperty("DeviceId")
    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    @JsonProperty("DeviceName")
    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    @JsonProperty("DeviceNumber")
    public String getDev_number() {
        return dev_number;
    }

    public void setDev_number(String dev_number) {
        this.dev_number = dev_number;
    }

    @JsonProperty("OrgName")
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @JsonProperty("AlarmType")
    public String getAlarm_type_id() {
        return alarm_type_id;
    }

    public void setAlarm_type_id(String alarm_type_id) {
        this.alarm_type_id = alarm_type_id;
    }

    @JsonProperty("FenceAlarmType")
    public String getFence_alarm_type() {
        return fence_alarm_type;
    }

    public void setFence_alarm_type(String fence_alarm_type) {
        this.fence_alarm_type = fence_alarm_type;
    }

    @JsonProperty("FenceId")
    public String getFence_id() {
        return fence_id;
    }

    public void setFence_id(String fence_id) {
        this.fence_id = fence_id;
    }

    @JsonProperty("FenceAlarmTypeText")
    public String getFence_alarm_type_text() {
        return fence_alarm_type_text;
    }

    public void setFence_alarm_type_text(String fence_alarm_type_text) {
        this.fence_alarm_type_text = fence_alarm_type_text;
    }

    @JsonProperty("AlarmTypeText")
    public String getAlarm_type_text() {
        return alarm_type_text;
    }

    public void setAlarm_type_text(String alarm_type_text) {
        this.alarm_type_text = alarm_type_text;
    }


}
