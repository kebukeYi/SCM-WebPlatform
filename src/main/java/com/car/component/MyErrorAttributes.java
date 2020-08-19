package com.car.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

//给容器中加入我们自己定义的ErrorAttributes
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    private static Logger logger = LoggerFactory.getLogger(MyErrorAttributes.class);

    //返回值的map就是页面和json能获取的所有字段
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
//        logger.warn("进入自定义异常数据处理");
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        map.put("company","error");

        //我们的异常处理器携带的数据
//        Map<String,Object> ext = (Map<String, Object>) webRequest.getAttribute("ext", 0);
//        ext.put("msg", ExceptionSetting.PAGENOTEXIST);
//        map.put("ext",ext);
        return map;
    }
}
