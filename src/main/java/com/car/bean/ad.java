package com.car.bean;

import lombok.Data;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/9  12:58
 * @Description
 */
@Data
public class ad<T> {

    private List<T> data;
    private int page;
    private int total;
    private int limit;
    private boolean IsExcel;
}
