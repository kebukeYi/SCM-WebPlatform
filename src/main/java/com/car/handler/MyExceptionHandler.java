package com.car.handler;

import com.car.exception.PageNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/6  14:24
 * @Description
 */
@ControllerAdvice
public class MyExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    //1、浏览器客户端返回的都是json
//    @ResponseBody
//    @ExceptionHandler(UserNotExistException.class)
//    public Map<String,Object> handleException(Exception e){
//        Map<String,Object> map = new HashMap<>();
//        map.put("code","user.notexist");
//        map.put("message",e.getMessage());
//        return map;
//    }


    @ExceptionHandler(PageNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request) {
        logger.warn("进入 404  Handler");
        Map<String, Object> map = new HashMap<>();
        //传入我们自己的错误状态码  4xx 5xx
        /**
         * Integer statusCode = (Integer) request
         .getAttribute("javax.servlet.error.status_code");
         */
        request.setAttribute("javax.servlet.error.status_code", 404);
        map.put("code", "404");
        map.put("message", "请求路径不存在！");
        request.setAttribute("ext", map);
        //转发到/error
        return "forward:/error";
    }


}
