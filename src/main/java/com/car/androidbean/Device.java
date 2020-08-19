package com.car.androidbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/26  15:27
 * @Description
 */
public class Device {

    private String imei;
    private String name;//名称
    private String number;//车牌号
    private String phone;//物联卡号码
    private String is_iot_card;
    private String tel;//联系人电话
    private String group_id;
    private String group_name;
    private String dev_type;
    private String client_product_type;
    private String owner;//联系人
    private String in_time;
    private String goome_card;
    private String out_time;//过期时间
    private String sudu;
    private boolean is_enable;//是否可用
    private String enable_time;//可用时间
    private boolean efence_support;//是否支持栅栏
    private String remark;

    public Device() {
    }

    public Device(String imei, String name, String number, String phone, String is_iot_card, String tel, String group_id, String group_name, String dev_type, String client_product_type, String owner, String in_time, String goome_card, String out_time, String sudu, boolean is_enable, String enable_time, boolean efence_support, String remark) {
        this.imei = imei;
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.is_iot_card = is_iot_card;
        this.tel = tel;
        this.group_id = group_id;
        this.group_name = group_name;
        this.dev_type = dev_type;
        this.client_product_type = client_product_type;
        this.owner = owner;
        this.in_time = in_time;
        this.goome_card = goome_card;
        this.out_time = out_time;
        this.sudu = sudu;
        this.is_enable = is_enable;
        this.enable_time = enable_time;
        this.efence_support = efence_support;
        this.remark = remark;
    }

    @JsonProperty("imei")
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("is_iot_card")
    public String getIs_iot_card() {
        return is_iot_card;
    }

    public void setIs_iot_card(String is_iot_card) {
        this.is_iot_card = is_iot_card;
    }

    @JsonProperty("tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @JsonProperty("group_id")
    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @JsonProperty("group_name")
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @JsonProperty("dev_type")
    public String getDev_type() {
        return dev_type;
    }

    public void setDev_type(String dev_type) {
        this.dev_type = dev_type;
    }

    @JsonProperty("client_product_type")
    public String getClient_product_type() {
        return client_product_type;
    }

    public void setClient_product_type(String client_product_type) {
        this.client_product_type = client_product_type;
    }

    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @JsonProperty("in_time")
    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    @JsonProperty("goome_card")
    public String getGoome_card() {
        return goome_card;
    }

    public void setGoome_card(String goome_card) {
        this.goome_card = goome_card;
    }

    @JsonProperty("out_time")
    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    @JsonProperty("sudu")
    public String getSudu() {
        return sudu;
    }

    public void setSudu(String sudu) {
        this.sudu = sudu;
    }

    @JsonProperty("is_enable")
    public boolean getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(boolean is_enable) {
        this.is_enable = is_enable;
    }

    @JsonProperty("enable_time")
    public String getEnable_time() {
        return enable_time;
    }

    public void setEnable_time(String enable_time) {
        this.enable_time = enable_time;
    }

    @JsonProperty("efence_support")
    public boolean getEfence_support() {
        return efence_support;
    }

    public void setEfence_support(boolean efence_support) {
        this.efence_support = efence_support;
    }

    @JsonProperty("remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
