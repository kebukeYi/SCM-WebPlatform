package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:54
 * @Description
 */
public class Mileage {

    private String id;
    private String user_id;
    private String group_id;
    private String group_name;

    private String dev_name;
    private String dev_number;

    private int model_type;
    private String model_type_name;

    private String catalogue_id;//所属目录id          有
    private String cata_name;

    private String lng;
    private String lat;
    private String lnglat;

    private String receive_time;
    private String location_time;

    private String mileage;//历程
    private String stroke_date; //日期


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getModel_type() {
        return model_type;
    }

    public void setModel_type(int model_type) {
        this.model_type = model_type;
    }

    public String getCatalogue_id() {
        return catalogue_id;
    }

    public void setCatalogue_id(String catalogue_id) {
        this.catalogue_id = catalogue_id;
    }

    public String getCata_name() {
        return cata_name;
    }

    public void setCata_name(String cata_name) {
        this.cata_name = cata_name;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocation_time() {
        return location_time;
    }

    public void setLocation_time(String location_time) {
        this.location_time = location_time;
    }

    @JsonProperty("Mileage")
    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    @JsonProperty("StrokeDate")
    public String getStroke_date() {
        return stroke_date;
    }

    public void setStroke_date(String stroke_date) {
        this.stroke_date = stroke_date;
    }

    @JsonProperty("ReceiveTime")
    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    @JsonProperty("DeviceNumber")
    public String getDev_number() {
        return dev_number;
    }

    public void setDev_number(String dev_number) {
        this.dev_number = dev_number;
    }


    @JsonProperty("OrgId")
    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @JsonProperty("OrgName")
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @JsonProperty("DeviceName")
    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    @JsonProperty("LngLat")
    public String getLnglat() {
        return lnglat;
    }

    public void setLnglat(String lnglat) {
        this.lnglat = lnglat;
    }


    @JsonProperty("ModelTypeText")
    public String getModel_type_name() {
        return model_type_name;
    }

    public void setModel_type_name(String model_type_name) {
        this.model_type_name = model_type_name;
    }

    @JsonProperty("LastLocationTime")
    public String getLast_location_time() {
        return location_time;
    }

    public void setLast_location_time(String last_location_time) {
        this.location_time = last_location_time;
    }

}
