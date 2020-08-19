package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * {"Status":1,"Result":{"data":[{
 * "DeviceName":"181219310018261","DeviceNumber":"181219310018261",
 * "ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,
 * "LocationTime":"0001-01-01T00:00:00","OrgName":"C180731",
 * "Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null
 * ,"CarModel":"","YesterdayMileage":3.92,"MonthMileage":3.92,"YearMileage":1417.7800000000002
 * },{"DeviceName":"181219310018291","DeviceNumber":"181219310018291","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C180731","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.02,"MonthMileage":0.02,"YearMileage":0.02},{"DeviceName":"181219310018355","DeviceNumber":"181219310018355","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C180731","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":89.82,"MonthMileage":220.29999999999998,"YearMileage":220.29999999999998},{"DeviceName":"181219310018586","DeviceNumber":"181219310018586","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C180731","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.0,"MonthMileage":0.0,"YearMileage":0.0},{"DeviceName":"181219310018589","DeviceNumber":"181219310018589","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C180731","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":1.44,"MonthMileage":6.5600000000000005,"YearMileage":6.57},{"DeviceName":"181219310018619","DeviceNumber":"181219310018619","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":true,"OnlineStatu":"在线","LocationTime":"2020-01-19T04:50:12","OrgName":"C180731","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":"北斗","DeviceType":"有线","LngLat":"126.65082547698,45.7885083268257","CarModel":"","YesterdayMileage":7.39,"MonthMileage":764.41000000000008,"YearMileage":764.41},{"DeviceName":"181219310018648","DeviceNumber":"181219310018648","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C180731","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.01,"MonthMileage":0.01,"YearMileage":0.01},{"DeviceName":"181219400018891","DeviceNumber":"181219400018891","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C181018","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.0,"MonthMileage":0.0,"YearMileage":0.0},{"DeviceName":"181219400018892","DeviceNumber":"181219400018892","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C181018","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.0,"MonthMileage":0.0,"YearMileage":0.0},{"DeviceName":"181219400018893","DeviceNumber":"181219400018893","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C181018","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.0,"MonthMileage":0.0,"YearMileage":0.0},{"DeviceName":"181219400018894","DeviceNumber":"181219400018894","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C181018","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.0,"MonthMileage":0.0,"YearMileage":0.0},{"DeviceName":"181219400018895","DeviceNumber":"181219400018895","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C181018","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.0,"MonthMileage":0.0,"YearMileage":0.0},{"DeviceName":"181219400018896","DeviceNumber":"181219400018896","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C181018","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.01,"MonthMileage":0.01,"YearMileage":0.01},{"DeviceName":"181219400018897","DeviceNumber":"181219400018897","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C181018","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.0,"MonthMileage":0.0,"YearMileage":0.0},{"DeviceName":"181219400018900","DeviceNumber":"181219400018900","ModelName":"C18-TRV","ModelType":0,"PlateNo":null,"Online":false,"OnlineStatu":null,"LocationTime":"0001-01-01T00:00:00","OrgName":"C181018","Brand":null,"Model":null,"Owner":null,"Phone":null,"LocationType":null,"DeviceType":null,"LngLat":null,"CarModel":"","YesterdayMileage":0.0,"MonthMileage":0.0,"YearMileage":0.0}],"page":1,"total":1657,"limit":15,"IsExcel":false},"ErrorMessage":null}
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class DeviceLocationInfo {


    private String dev_name;
    private String dev_number;
    private String model_name;
    private String model_type_text;
    private String onlineStatus; //TerminalMonitor .html  OnlineStatus  设备状态
    private String expire_date;//过期的日子
    private String group_name;
    private String phone;
    private String lnglat;
    private String location_type;
    private String location_time;
//    private String model_type;


    @JsonProperty("LngLat")
    public String getLng_lat() {
        return lnglat;
    }

    public void setLng_lat(String lng_lat) {
        this.lnglat = lng_lat;
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

    @JsonProperty("OnlineStatus")
    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        onlineStatus = onlineStatus;
    }

    @JsonProperty("ModelTypeText")
    public String getModel_type_text() {
        return model_type_text;
    }

    public void setModel_type_text(String model_type_text) {
        this.model_type_text = model_type_text;
    }

    @JsonProperty("ExpireDate")
    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    @JsonProperty("LocationTime")
    public String getLocation_time() {
        return location_time;
    }

    public void setLocation_time(String location_time) {
        this.location_time = location_time;
    }

    @JsonProperty("OrgName")
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @JsonProperty("Phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("LocationType")
    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }
}