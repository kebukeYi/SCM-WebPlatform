package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  14:36
 * @Description
 */
@Data
public class RoundAlarm {

    @JsonProperty("Alarms")
    private List<Alarm> Alarms;

    @JsonProperty("Total")
    private int Total;

    @JsonProperty("LastTime")
    private String LastTime;

}
