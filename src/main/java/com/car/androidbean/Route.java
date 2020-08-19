package com.car.androidbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
安卓
 */
public class Route {
    private String lng;
    private String lat;
    private String gps_time;
    private String sysTime;
    private double course;
    private String speed;
    private int sequenceNo;
@JsonProperty("sysTime")
    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
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
@JsonProperty("gps_time")
    public String getGps_time() {
        return gps_time;
    }

    public void setGps_time(String gps_time) {
        this.gps_time = gps_time;
    }
@JsonProperty("course")
    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }
@JsonProperty("speed")
    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
@JsonProperty("sequenceNo")
    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
}
