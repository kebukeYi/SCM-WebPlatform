package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/5  16:26
 * @Description
 */
public class Groups {

    List<Group> data;
    private int page;
    private int total;
    private int limit;
    private boolean IsExcel;


    @JsonProperty("data")
    public List<Group> getData() {
        return data;
    }

    public void setData(List<Group> data) {
        this.data = data;
    }

    @JsonProperty("page")
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @JsonProperty("total")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @JsonProperty("limit")
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @JsonProperty("IsExcel")
    public boolean isExcel() {
        return IsExcel;
    }

    public void setExcel(boolean excel) {
        IsExcel = excel;
    }





}
