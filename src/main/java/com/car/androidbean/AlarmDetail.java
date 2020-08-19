package com.car.androidbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/26  16:34
 * @Description
 */
public class AlarmDetail {

    private String id;//报警ID
    private int alarm_type_id;//报警类型Id
    private String alarm_type;
    private String alarm_time;
    private String gps_time;//gps时间
    private String dev_name;//设备名称
    private String dev_number;
    private String dev_type;//设备类型
    private int speed;
    private String imei;
    private String gps_status;//gps状态
    private String dir;//
    private String address;//地址
    private String lng;
    private String lat;


    @JsonProperty("dev_number")
    public String getDev_number() {
        return dev_number;
    }

    public void setDev_number(String dev_number) {
        this.dev_number = dev_number;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
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
    @JsonProperty("alarm_time")
    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }
    @JsonProperty("gps_time")
    public String getGps_time() {
        return gps_time;
    }

    public void setGps_time(String gps_time) {
        this.gps_time = gps_time;
    }
    @JsonProperty("dev_name")
    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }
    @JsonProperty("dev_type")
    public String getDev_type() {
        return dev_type;
    }

    public void setDev_type(String dev_type) {
        this.dev_type = dev_type;
    }
    @JsonProperty("speed")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    @JsonProperty("imei")
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
    @JsonProperty("gps_status")
    public String getGps_status() {
        return gps_status;
    }

    public void setGps_status(String gps_status) {
        this.gps_status = gps_status;
    }
    @JsonProperty("dir")
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @JsonProperty("lng")
    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
    @JsonProperty("lat")
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
