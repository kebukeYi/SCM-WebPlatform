package com.car.mysql.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/13  18:39
 * @Description
 */
@Configuration
// 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableTransactionManagement
public class SessionFactoryConfig {

    /**
     * mybatis 配置路径
     */
    private static String MYBATIS_CONFIG = "mybatis/mybatis-config.xml";

    @Autowired
    private DataSource dataSource;


    /***
     *  创建sqlSessionFactoryBean
     *  并且设置configtion 如驼峰命名.等等
     *  设置mapper 映射路径
     *  设置datasource数据源
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        // 设置mybatis configuration 扫描路径
        sqlSessionFactory.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        // 设置datasource
        sqlSessionFactory.setDataSource(dataSource);
        return sqlSessionFactory;
    }

}
