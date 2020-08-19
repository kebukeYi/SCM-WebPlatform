package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * Auto-generated: 2020-01-19 7:33:59
 * {"Status":1,"Result":{"Owner":null,"Phone":null,"PlateType":"","VIN":null,"CarColor":null,"CarType":null,"CarId":"00000000-0000-0000-0000-000000000000","Brand":null,"Model":null,"OrgIdentity":null,"CreateTime":"2019-10-18T11:42:58.617","DeviceNumber":"181219400018893","DevicePassword":"123456","Id":"15065ef9-3ea7-418e-9c9f-aaea00c11412","LastUpdateTime":"2019-10-18T11:42:58.617","MFDate":"2019-10-18T00:00:00","Name":"181219400018893","OrgnizationId":"bb765b00-4d60-4de0-aebd-aaea00b7f809","Remark":"","Simcard":"","OrgName":"C181018","ModeId":36,"ModelName":"C18-TRV","ModelType":0,"ModelTypeText":"有线","ICCID":"89860408091870176429","PlateNo":null,"ExpireDate":"2020-10-18T00:00:00"},"ErrorMessage":null}
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class DeviceDetail {


    private String location_time;
    private String dev_number;
    private String last_update_time;
    private String dev_name;
    private String group_id;
    private String imsi;
    private String group_name;
    private String model_id;
    private String model_name;
    private String model_type;
    private String model_type_text;
    private String expire_date;
    private String mf_date;//出厂日期


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

    @JsonProperty("CreateTime")
    public String getLocation_time() {
        return location_time;
    }

    public void setLocation_time(String location_time) {
        this.location_time = location_time;
    }

    @JsonProperty("DeviceNumber")
    public String getDev_number() {
        return dev_number;
    }

    public void setDev_number(String dev_number) {
        this.dev_number = dev_number;
    }

    @JsonProperty("LastUpdateTime")
    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    @JsonProperty("Name")
    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    @JsonProperty("OrganizationId")
    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @JsonProperty("Simcard")
    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    @JsonProperty("ModelTypeText")
    public String getModel_type_text() {
        return model_type_text;
    }

    public void setModel_type_text(String model_type_text) {
        this.model_type_text = model_type_text;
    }


    @JsonProperty("OrgName")
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
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


}