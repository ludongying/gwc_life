package com.seven.gwc.modular.sync.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @author : zzl
 * @Date: 2020-03-13
 * @description :
 */
@Configuration
public class MasterConfig {

    @Value("${genQo.Url}")
    private String url;

    @Value("${genQo.UserName}")
    private String user;

    @Value("${genQo.Password}")
    private String password;

    @Value("${genQo.Driver}")
    private String driverClass;

    public DataSource masterDataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(driverClass);
//        dataSource.setUrl(url);
//        dataSource.setUsername(user);
//        dataSource.setPassword(password);
//        return data
        return null;
    }

    public SqlSessionFactory masterSqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource());
        return sessionFactory.getObject();
    }

}
