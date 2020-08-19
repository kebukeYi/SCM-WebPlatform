package com.car.vo;

import com.car.androidbean.Children;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/26  15:21
 * @Description
 */
@Data
public class DevinfoVo<T> {

    int errcode;
    boolean success;
    String customer_id;
    String msg;
    boolean isBeyondLimit;
    List<Children> children;
    List<T> data;


    @JsonProperty("errcode")
    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    @JsonProperty("success")
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @JsonProperty("customer_id")
    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("isBeyondLimit")
    public boolean isBeyondLimit() {
        return isBeyondLimit;
    }

    public void setBeyondLimit(boolean beyondLimit) {
        isBeyondLimit = beyondLimit;
    }

    @JsonProperty("children")
    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    @JsonProperty("data")
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
