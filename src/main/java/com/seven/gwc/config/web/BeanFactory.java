package com.seven.gwc.config.web;

import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.lawrecord.data.local.LocData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :bean 工厂
 */
@Configuration
public class BeanFactory {


    @Bean
    public LocData locData(){
        return new LocData();
    }
    @Bean
    public FileManager fileManager(){
        return new FileManager();
    }

}
