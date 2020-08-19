package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/17  17:30
 * @Description
 */

public class StopDeviceDetail {

    String dev_number;
    String dev_name;
    String group_name;
    String lnglat;
    String start_time;
    String end_time;
    String stop_minutes;//停留时长
    String address;



    @JsonProperty("LngLat")
    public String getLnglat() {
        return lnglat;
    }

    public void setLnglat(String lnglat) {
        this.lnglat = lnglat;
    }

    @JsonProperty("DeviceNumber")
    public String getDev_number() {
        return dev_number;
    }

    public void setDev_number(String dev_number) {
        this.dev_number = dev_number;
    }

    @JsonProperty("DeviceName")
    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    @JsonProperty("OrgName")
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @JsonProperty("StartTime")
    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    @JsonProperty("EndTime")
    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @JsonProperty("IntervalText")
    public String getStop_minutes() {
        return stop_minutes;
    }

    public void setStop_minutes(String stop_minutes) {
        this.stop_minutes = stop_minutes;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
