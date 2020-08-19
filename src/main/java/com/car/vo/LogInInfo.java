package com.car.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogInInfo {

    private boolean success;
    private String data;
    private String msg;

    @JsonProperty("success")
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @JsonProperty("data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
