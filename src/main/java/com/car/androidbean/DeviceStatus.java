package com.car.androidbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/26  16:07
 * @Description
 */
public class DeviceStatus {

    private String imei;
    private String device_info;
    private String device_info_new;
    private String seconds;
    private String gps_time;
    private String sys_time;
    private String heart_time;
    private String server_time;
    private String lng;
    private String lat;
    private String course;
    private String speed;
    private String status;
    private String acc;
    private String acc_seconds;
    private String voice_status;
    private String voice_gid;
    private String smart_record;
    private String trickle_power;
    private String record_time;
    private String record_len;
    private String pos_accuracy;
    private String srec_volume;
    private String battery_life;

    @JsonProperty("imei")
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
    @JsonProperty("device_info")
    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }
    @JsonProperty("device_info_new")
    public String getDevice_info_new() {
        return device_info_new;
    }

    public void setDevice_info_new(String device_info_new) {
        this.device_info_new = device_info_new;
    }
    @JsonProperty("seconds")
    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }
    @JsonProperty("gps_time")
    public String getGps_time() {
        return gps_time;
    }

    public void setGps_time(String gps_time) {
        this.gps_time = gps_time;
    }
    @JsonProperty("sys_time")
    public String getSys_time() {
        return sys_time;
    }

    public void setSys_time(String sys_time) {
        this.sys_time = sys_time;
    }
    @JsonProperty("heart_time")
    public String getHeart_time() {
        return heart_time;
    }

    public void setHeart_time(String heart_time) {
        this.heart_time = heart_time;
    }
    @JsonProperty("server_time")
    public String getServer_time() {
        return server_time;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
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
    @JsonProperty("course")
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    @JsonProperty("speed")
    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @JsonProperty("acc")
    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }
    @JsonProperty("acc_seconds")
    public String getAcc_seconds() {
        return acc_seconds;
    }

    public void setAcc_seconds(String acc_seconds) {
        this.acc_seconds = acc_seconds;
    }
    @JsonProperty("voice_status")
    public String getVoice_status() {
        return voice_status;
    }

    public void setVoice_status(String voice_status) {
        this.voice_status = voice_status;
    }
    @JsonProperty("voice_gid")
    public String getVoice_gid() {
        return voice_gid;
    }

    public void setVoice_gid(String voice_gid) {
        this.voice_gid = voice_gid;
    }
    @JsonProperty("smart_record")
    public String getSmart_record() {
        return smart_record;
    }

    public void setSmart_record(String smart_record) {
        this.smart_record = smart_record;
    }
    @JsonProperty("trickle_power")
    public String getTrickle_power() {
        return trickle_power;
    }

    public void setTrickle_power(String trickle_power) {
        this.trickle_power = trickle_power;
    }
    @JsonProperty("record_time")
    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }
    @JsonProperty("record_len")
    public String getRecord_len() {
        return record_len;
    }

    public void setRecord_len(String record_len) {
        this.record_len = record_len;
    }
    @JsonProperty("pos_accuracy")
    public String getPos_accuracy() {
        return pos_accuracy;
    }

    public void setPos_accuracy(String pos_accuracy) {
        this.pos_accuracy = pos_accuracy;
    }
    @JsonProperty("srec_volume")
    public String getSrec_volume() {
        return srec_volume;
    }

    public void setSrec_volume(String srec_volume) {
        this.srec_volume = srec_volume;
    }
    @JsonProperty("battery_life")
    public String getBattery_life() {
        return battery_life;
    }

    public void setBattery_life(String battery_life) {
        this.battery_life = battery_life;
    }
}
