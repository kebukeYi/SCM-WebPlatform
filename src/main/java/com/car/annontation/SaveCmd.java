package com.car.annontation;

import java.lang.annotation.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/15  17:12
 * @Description 下达指令注解
 */
@Documented
//JVM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
//该注解用于方法声明
@Target(ElementType.METHOD)
public @interface SaveCmd {

}
