package com.seven.gwc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication
@MapperScan(basePackages = "com.seven.gwc.modular.*.dao")
public class GwcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GwcApplication.class, args);
    }

}

