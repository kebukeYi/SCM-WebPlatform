package com.car.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  12:15
 * @Description
 */
public class RootVo<T> implements Serializable {

    private int Status;
    private T Result;
    private String ErrorMessage = null;

    @Override
    public String toString() {
        return "RootVo{" +
                "Status=" + Status +
                ", Result=" + Result +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                '}';
    }

    @JsonProperty("Status")
    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    @JsonProperty("Result")
    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    @JsonProperty("ErrorMessage")
    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
