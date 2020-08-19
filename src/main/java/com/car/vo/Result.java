package com.car.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Result<T> {
   private int errcode;
   private boolean success;
   private String msg;
   private Map<String, T> data;

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
@JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
@JsonProperty("data")
    public Map<String, T> getData() {
        return data;
    }

    public void setData(Map<String, T> data) {
        this.data = data;
    }
}
