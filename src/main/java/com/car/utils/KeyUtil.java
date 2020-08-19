package com.car.utils;

import java.util.Random;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/5  23:46
 * @Description
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }

    public static void main(String[] args) {
        System.out.println(genUniqueKey());
        System.out.println( System.currentTimeMillis());
    }
}
