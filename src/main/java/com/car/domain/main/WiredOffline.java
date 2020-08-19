package com.car.domain.main;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  18:13
 * @Description
 */

public class WiredOffline {

    private int total;
    private List<Integer> data;

    @JsonProperty("total")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @JsonProperty("data")
    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "WiredOffline{" +
                "data=" + data +
                '}';
    }
}
