package com.car.exception;


import com.car.setting.ExceptionSetting;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/6  14:16
 * @Description
 */
public class PageNotExistException extends RuntimeException {
    public PageNotExistException() {
        super(ExceptionSetting.PAGENOTEXIST);
    }

}
