package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/5  16:24
 * @Description
 */
public class Group {

    String OrgId;
    String OrgName;
    String ParentName;
    String FenceId;

    String AlarmType;//暂时无用
    String AlarmTypeText;


    String FenceAlarmType;
    String FenceAlarmTypeText;
    String BindTime;


    @JsonProperty("FenceAlarmType")
    public String getFenceAlarmType() {
        return FenceAlarmType;
    }

    public void setFenceAlarmType(String fenceAlarmType) {
        FenceAlarmType = fenceAlarmType;
    }

    @JsonProperty("FenceAlarmTypeText")
    public String getFenceAlarmTypeText() {
        return FenceAlarmTypeText;
    }

    public void setFenceAlarmTypeText(String fenceAlarmTypeText) {
        FenceAlarmTypeText = fenceAlarmTypeText;
    }

    @JsonProperty("BindTime")
    public String getBindTime() {
        return BindTime;
    }

    public void setBindTime(String bindTime) {
        BindTime = bindTime;
    }

    @JsonProperty("OrgId")
    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    @JsonProperty("OrgName")
    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    @JsonProperty("ParentName")
    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

    @JsonProperty("FenceId")
    public String getFenceId() {
        return FenceId;
    }

    public void setFenceId(String fenceId) {
        FenceId = fenceId;
    }

    @JsonProperty("AlarmType")
    public String getAlarmType() {
        return AlarmType;
    }

    public void setAlarmType(String alarmType) {
        AlarmType = alarmType;
    }

    @JsonProperty("AlarmTypeText")
    public String getAlarmTypeText() {
        return AlarmTypeText;
    }

    public void setAlarmTypeText(String alarmTypeText) {
        AlarmTypeText = alarmTypeText;
    }
}
