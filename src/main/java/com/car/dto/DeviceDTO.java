package com.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  12:20
 * @Description
 */

/*
public class Device
{
    private String DeviceName;
    private String DeviceNumber;
    private String ModelName;
    private int ModelType;
    private String PlateNo;
    private String Aliase;
    private boolean Online;
    private String Attention;
    private String LastUpdateTime;
    private String LocationTime;
    private int LocationType;
    private String Speed;
    private String Signal;
    private String Electricity;
    private String StopTime;
    private String Simcard;
    private String Status;
    private boolean Disabled;
    private String OrgName;
    private String OrgnizationId;
}

Lng: 126.650489601207
Lat: 45.7881378861487
DeviceNumber: "181219310018619"
DeviceName: "181219310018619"
Status: "NotDisassembly, Saving, ACCOFF, OilON, EleON, EleConnected, Stopping"
Alarm: "None"
StopTime: "2020-01-18T09:33:49"
ReceiveTime: "2020-01-18T15:59:39.9256141"
LocationTime: "2020-01-18T09:33:49"
LocationType: 1
Online: true
Direct: 326.48
Electricity: 90
Satellites: 0
Signal: 100
Speed: 0
ModelType: 0
Mileage: 0
OuterVoltage: null

 */
public class DeviceDTO {

    private String dev_name;
    private String dev_number;
    private String model_name;
    private int model_type;
    private short mileage;
    private String aliase;
    private boolean online;
    private boolean attention;
    private boolean activation;
    private String last_update_time;
    private String location_time;
    private int location_type;
    private int speed;
    private int signal;
    private int satellites;
    private int battery_life;
    private String stop_time;
    private String simcard;
    private String status;//NotDisassembly, ContinueLocation, ACCOFF, OilON, EleON

    private boolean disabled;
    private String group_name;
    private String group_id;
    private String user_id;
    private String catalogue_id;//所属目录id          有
    private String iccid;
    private String lng;
    private String lat;
    private String address;
    private String direct;
    private String receive_time;//信号时间
    private String alarm;
    private String out_time;//过期时间                     有

    private String online_status;// Stop:24:5:7:21

    @JsonProperty("Activation")
    public boolean isActivation() {
        return activation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    @JsonProperty("OnlineStatus")
    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    @JsonProperty("Attention")
    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
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

    @JsonProperty("ModelName")
    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    @JsonProperty("Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("ModelType")
    public int getModel_type() {
        return model_type;
    }

    public void setModel_type(int model_type) {
        this.model_type = model_type;
    }

    @JsonProperty("LastUpdateTime")
    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    @JsonProperty("LocationTime")
    public String getLocation_time() {
        return location_time;
    }

    public void setLocation_time(String location_time) {
        this.location_time = location_time;
    }

    @JsonProperty("LocationType")
    public int getLocation_type() {
        return location_type;
    }

    public void setLocation_type(int location_type) {
        this.location_type = location_type;
    }

    @JsonProperty("Electricity")
    public int getBattery_life() {
        return battery_life;
    }

    public void setBattery_life(int battery_life) {
        this.battery_life = battery_life;
    }

    @JsonProperty("StopTime")
    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    @JsonProperty("OrgName")
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @JsonProperty("OrgId")
    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @JsonProperty("UserId")
    public String getUser_id() {
        return user_id;
    }


    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @JsonProperty("CatalogueId")
    public String getCatalogue_id() {
        return catalogue_id;
    }

    public void setCatalogue_id(String catalogue_id) {
        this.catalogue_id = catalogue_id;
    }

    @JsonProperty("ICCID")
    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    @JsonProperty("ReceiveTime")
    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    @JsonProperty("ExpireDate")
    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }


    @JsonProperty("Lng")
    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @JsonProperty("Lat")
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("Direct")
    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    @JsonProperty("Alarm")
    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }



    @JsonProperty("Mileage")
    public short getMileage() {
        return mileage;
    }

    public void setMileage(short mileage) {
        this.mileage = mileage;
    }


    @JsonProperty("Aliase")
    public String getAliase() {
        return aliase;
    }

    public void setAliase(String aliase) {
        this.aliase = aliase;
    }

    @JsonProperty("Online")
    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }


    @JsonProperty("Speed")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @JsonProperty("Signal")
    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }


    @JsonProperty("Satellites")
    public int getSatellites() {
        return satellites;
    }

    public void setSatellites(int satellites) {
        this.satellites = satellites;
    }


    @JsonProperty("Simcard")
    public String getSimcard() {
        return simcard;
    }

    public void setSimcard(String simcard) {
        this.simcard = simcard;
    }

    @JsonProperty("Disabled")
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }


}
