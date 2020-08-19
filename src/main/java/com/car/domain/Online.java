package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/5  16:45
 * @Description
 */
public class Online {

    String OnlineDate;
    boolean IsOnline;

    @JsonProperty("OnlineDate")
    public String getOnlineDate() {
        return OnlineDate;
    }

    public void setOnlineDate(String onlineDate) {
        OnlineDate = onlineDate;
    }

    @JsonProperty("IsOnline")
    public boolean getIsOnline() {
        return IsOnline;
    }

    public void setIsOnline(boolean isOnline) {
        IsOnline = isOnline;
    }
}
