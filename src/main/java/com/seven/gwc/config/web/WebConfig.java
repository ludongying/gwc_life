package com.seven.gwc.config.web;

import com.seven.gwc.config.constant.ConfigConsts;
import com.seven.gwc.config.interceptor.AttributeSetInterceptor;
import com.seven.gwc.config.properties.GwcProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web 配置类
 *
 * @author GD
 * @date 2020/2/20 11:20
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Autowired
    private GwcProperties gwcProperties;

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (gwcProperties.getSwaggerOpen()) {
            registry.addResourceHandler("document.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/META-INF/resources/static/");
        }
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 增加对rest api鉴权的spring mvc拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtConfig()).addPathPatterns("/gwcApi/**");
        registry.addInterceptor(new AttributeSetInterceptor()).excludePathPatterns(ConfigConsts.NONE_PERMISSION_RES).addPathPatterns("/**");
    }

}
