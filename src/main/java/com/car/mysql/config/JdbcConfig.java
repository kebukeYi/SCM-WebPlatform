package com.car.mysql.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/13  18:38
 * @Description 原文链接：https://blog.csdn.net/BLUE5945/article/details/79203178
 */
@PropertySource(value = "classpath:jdbc.properties")
@Component
public class JdbcConfig {

    /**
     * 数据库用户名
     */
    @Value("${spring.datasource.username}")
    private String userName;
    /**
     * 驱动名称
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;
    /**
     * 数据库连接url
     */
    @Value("${spring.datasource.url}")
    private String url;
    /**
     * 数据库密码
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 数据库连接池初始化大小
     */
    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    /**
     * 数据库连接池最小最小连接数
     */
    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    /**
     * 数据库连接池最大连接数
     */
    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    /**
     * 获取连接等待超时的时间
     */
    @Value("${spring.datasource.maxWait}")
    private long maxWait;

    /**
     * 多久检测一次
     */
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    /**
     * 连接在池中最小生存的时间
     */
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;

    /**
     * 测试连接是否有效的sql
     */
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    /**
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，检测连接是否有效
     */
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    /**
     * 申请连接时,检测连接是否有效
     */
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    /**
     * 归还连接时,检测连接是否有效
     */
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    /**
     * PSCache大小
     */
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    /**
     * 通过别名的方式配置扩展插件
     */
//    @Value("${spring.datasource.filters}")
//    private String filters;




    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

//    public String getFilters() {
//        return filters;
//    }
//
//    public void setFilters(String filters) {
//        this.filters = filters;
//    }
}




