package com.car.bean;

import com.car.domain.Alarm;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/9  12:03
 * @Description
 */
@Data
public class UserOrgs {


    private List<UserOrg> data;
    private int page;
    private int total;
    private int limit;
    private boolean IsExcel;

    @JsonProperty("data")
    public List<UserOrg> getData() {
        return data;
    }

    public void setData(List<UserOrg> data) {
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
