package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  22:13
 * @Description
 */
@Data
public class Mileages {
    private List<Mileage> data;
    private int page;
    private int total;
    private int limit;
    private boolean IsExcel;

    @JsonProperty("data")
    public List<Mileage> getData() {
        return data;
    }

    public void setData(List<Mileage> data) {
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
