/*
 *
 *  *
 *  * * TONFUN CONFIDENTIAL AND PROPRIETARY.
 *  * *
 *  * * This source is the sole property of Tonfun CO.,Ltd. Reproduction or Utilization of this source
 *  * * in whole or in part is forbidden without the written consent of Tonfun CO.,Ltd.
 *  * * 此文件所有权仅归天津同丰信息技术有限公司所有。
 *  * * 未经天津同丰信息技术有限公司书面同意，严禁对此文件的全部或部分进行复制或使用。
 *  * *
 *  * * Tonfun CONFIDENTIAL.
 *  * * Copyright 2011-2019 Tonfun Corporation All Rights Reserved.
 *  * * 天津同丰信息技术有限公司机密。
 *  * * Copyright 2011-2019 天津同丰信息技术有限公司保留所有权利。
 *  * *--------------------------------------------------------------------------------------------------
 *  * *
 *  * *  文件名: EhCacheConfig.java
 *  * *  描    述:
 *  * *  作    者: GD
 *  * *  当前修改时间：2019年07月24日 14:30:01
 *  * *  上次修改时间：2019年07月24日 13:53:38
 *  * *------------------------------------------------------------------------------------------------
 *
 */
package com.seven.gwc.core.cache;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * ehcache配置
 */
@Configuration
@EnableCaching
public class EhCacheConfig {

    /**
     * EhCache的配置
     */
    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cacheManager) {
        return new EhCacheCacheManager(cacheManager);
    }

    /**
     * EhCache的配置
     */
    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }
}
