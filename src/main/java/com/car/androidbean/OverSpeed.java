package com.car.androidbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
车辆设置信息  超速
安卓
 */
public class OverSpeed {
    private String speed;
    private String overspeed_flag;
@JsonProperty("speed")
    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
@JsonProperty("overspeed_flag")
    public String getOverspeed_flag() {
        return overspeed_flag;
    }

    public void setOverspeed_flag(String overspeed_flag) {
        this.overspeed_flag = overspeed_flag;
    }
}
