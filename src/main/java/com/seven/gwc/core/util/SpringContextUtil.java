package com.seven.gwc.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: GD
 * @Description: 获取ApplicationContext和Object的工具类
 * @Date: 2019/8/7 13:45
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public SpringContextUtil() {}

    /**
     * 实现ApplicationContext的方法
     * @param applicationContext 对象
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取ApplicationContext对象
     * @return 返回ApplicationContext对象
     */
    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    /**
     * 根据bean的beanName来查找对象
     */
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 根据bean的class来查找对象
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    private static void assertApplicationContext() {
        if (applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextUtil!");
        }
    }
}
