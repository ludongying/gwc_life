package com.seven.gwc.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * GWC项目配置
 */
@Component
@ConfigurationProperties(prefix = GwcProperties.PREFIX)
public class GwcProperties {
    public static final String PREFIX = "gwc";

    private Boolean swaggerOpen = false;
    private Boolean springSessionOpen = false;              //是否开启spring session
    private Integer sessionInvalidateTime = 30 * 60;        //session 失效时间（默认为30分钟 单位：秒）
    private Integer sessionValidationInterval = 15 * 60;    //session 验证失效时间（默认为15分钟 单位：秒）


    public static String getPREFIX() {
        return PREFIX;
    }

    public Boolean getSpringSessionOpen() {
        return springSessionOpen;
    }

    public void setSpringSessionOpen(Boolean springSessionOpen) {
        this.springSessionOpen = springSessionOpen;
    }

    public Integer getSessionInvalidateTime() {
        return sessionInvalidateTime;
    }

    public void setSessionInvalidateTime(Integer sessionInvalidateTime) {
        this.sessionInvalidateTime = sessionInvalidateTime;
    }

    public Integer getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(Integer sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public Boolean getSwaggerOpen() {
        return swaggerOpen;
    }

    public void setSwaggerOpen(Boolean swaggerOpen) {
        this.swaggerOpen = swaggerOpen;
    }
}
