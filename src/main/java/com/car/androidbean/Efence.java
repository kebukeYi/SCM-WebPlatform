package com.car.androidbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 车辆设置信息 围栏
 安卓
 */
public class Efence {
    private  String id;
    private  String user_id;
    private  String shape_type;
    private  String alarm_type;
    private  String validate_flag;
    private  String create_time;
    private  String phone_num;
    private  String remark;
    private  String shape_param;
@JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
@JsonProperty("user_id")
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
@JsonProperty("shape_type")
    public String getShape_type() {
        return shape_type;
    }

    public void setShape_type(String shape_type) {
        this.shape_type = shape_type;
    }
@JsonProperty("alarm_type")
    public String getAlarm_type() {
        return alarm_type;
    }

    public void setAlarm_type(String alarm_type) {
        this.alarm_type = alarm_type;
    }
@JsonProperty("validate_flag")
    public String getValidate_flag() {
        return validate_flag;
    }

    public void setValidate_flag(String validate_flag) {
        this.validate_flag = validate_flag;
    }
@JsonProperty("create_time")
    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
@JsonProperty("phone_num")
    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
@JsonProperty("remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
@JsonProperty("shape_param")
    public String getShape_param() {
        return shape_param;
    }

    public void setShape_param(String shape_param) {
        this.shape_param = shape_param;
    }
}
