package com.car.redis.bean;

import lombok.Data;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/18  12:58
 * @Description
 */
@Data
public class Point {

    String lat;
    String lng;

    public Point(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
