package com.car.influxdb.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String code;
    private String descr;
    private String descrE;
    private String createdBy;
    private String createdAt;

    private String time;
    private String tagCode;
    private String tagTime;


}