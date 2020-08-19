package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  23:18
 * @Description
 */
public class Car {

    private String id;
    private String user_id;

    private String group_id;
    private String group_name;

    private String catalogue_id;//所属目录id          有
    private String carName;

    private String PlateNo;//车牌号
    private String VIN;//车架号
    private String CarColor;
    private String CarType;
    private String Brand;//品牌
    private String Model;//型号
    private String Owner;
    private String Phone;
    private String ZipDeviceModels;//关联的终端
    private String Remark;





    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCatalogue_id() {
        return catalogue_id;
    }

    public void setCatalogue_id(String catalogue_id) {
        this.catalogue_id = catalogue_id;
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    @JsonProperty("PlateNo")
    public String getPlateNo() {
        return PlateNo;
    }

    public void setPlateNo(String plateNo) {
        PlateNo = plateNo;
    }

    @JsonProperty("VIN")
    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    @JsonProperty("CarColor")
    public String getCarColor() {
        return CarColor;
    }

    public void setCarColor(String carColor) {
        CarColor = carColor;
    }

    @JsonProperty("CarType")
    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    @JsonProperty("Brand")
    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    @JsonProperty("Model")
    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    @JsonProperty("Owner")
    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    @JsonProperty("Phone")
    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    @JsonProperty("ZipDeviceModels")
    public String getZipDeviceModels() {
        return ZipDeviceModels;
    }

    public void setZipDeviceModels(String zipDeviceModels) {
        ZipDeviceModels = zipDeviceModels;
    }

    @JsonProperty("Remark")
    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
