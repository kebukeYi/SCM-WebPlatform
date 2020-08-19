package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/16  16:35
 * @Description
 */
public class Terminal {

    private String id;

    private String iccid;//    89860308558948102419
    private String imsi;//      460071005183534

    private String dev_number;//设备号码
    private String dev_name;//设备名称   "goome-22875"

    private String plateNo;//车牌号

    private String model_id;// 0-所有型号  1-R10
    private String model_name;//R10
    private String model_type;// 1 0
    private String model_type_name;//

    private String user_id;
    private String group_id;//  0              有
    private String group_name; //
    private String catalogue_id;//所属目录id          有
    private String cata_name;


    private String creat_time;//创建时间

    private String receive_time;//信号时间
    private String location_time;//定位时间
    private String location_type;// 定位方式

    private String mf_date;//出厂日期
    private String expire_date;//过期日期

    private String device_password;//密码

    private String remark;//备注

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonProperty("DevicePassword")
    public String getDevice_password() {
        return device_password;
    }

    public void setDevice_password(String device_password) {
        this.device_password = device_password;
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @JsonProperty("ICCID")
    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }


    @JsonProperty("Simcard")
    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    @JsonProperty("DeviceNumber")
    public String getDev_number() {
        return dev_number;
    }

    public void setDev_number(String dev_number) {
        this.dev_number = dev_number;
    }

    @JsonProperty("Name")
    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    @JsonProperty("PlateNo")
    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    @JsonProperty("ModeId")
    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    @JsonProperty("ModelName")
    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    @JsonProperty("ModelType")
    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    @JsonProperty("ModelTypeText")
    public String getModel_type_name() {
        return model_type_name;
    }

    public void setModel_type_name(String model_type_name) {
        this.model_type_name = model_type_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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


    @JsonProperty("CreateTime")
    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    @JsonProperty("LastUpdateTime")
    public String getLocation_time() {
        return location_time;
    }

    public void setLocation_time(String location_time) {
        this.location_time = location_time;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    @JsonProperty("MFDate")
    public String getMf_date() {
        return mf_date;
    }

    public void setMf_date(String mf_date) {
        this.mf_date = mf_date;
    }

    @JsonProperty("ExpireDate")
    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }
}
