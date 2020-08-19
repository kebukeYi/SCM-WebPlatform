package com.car.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/5  23:25
 * @Description
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /*
    "/api/**","/Fence/**","/monitor/**","/TerminalMonitor/**","/playback/**","/follow/**","/map/**","/index/**"
    "/api/**","/index"
     - /**： 匹配所有路径
     - /admin/**：匹配 /admin/ 下的所有路径
     - /secure/*：只匹配 /secure/user，不匹配 /secure/user/info
 */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/", "/ad/**", "/login", "/isLogin", "/css/**", "/Content/**", "/images/**", "/fonts/**", "/json/**", "/Scripts/**");
    }

    // 设置跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                .allowCredentials(true)
                .maxAge(60 * 60 * 5);
    }
}
