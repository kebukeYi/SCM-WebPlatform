package com.car.domain.main;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/5  16:05
 * @Description
 */
public class Refresh {

    String LastTime;
    List Data=new ArrayList();

    @JsonProperty("LastTime")
    public String getLastTime() {
        return LastTime;
    }

    public void setLastTime(String lastTime) {
        LastTime = lastTime;
    }

    @JsonProperty("Data")
    public List getData() {
        return Data;
    }

    public void setData(List data) {
        this.Data = data;
    }
}
