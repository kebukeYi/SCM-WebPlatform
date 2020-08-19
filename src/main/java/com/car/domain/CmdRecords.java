package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/5  17:10
 * @Description
 */
public class CmdRecords {

    List<CmdRecord> data;
    private int page;
    private int total;
    private int limit;
    private boolean IsExcel;


    @JsonProperty("data")
    public List<CmdRecord> getData() {
        return data;
    }

    public void setData(List<CmdRecord> data) {
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
