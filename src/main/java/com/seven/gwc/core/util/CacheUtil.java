package com.seven.gwc.core.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.List;

/**
 * 缓存工具类
 */
@Slf4j
public class CacheUtil {

    private static final Object LOCKER = new Object();

    /**
     * 添加缓存
     *
     * @param cacheName 缓存名称,ehcache.xml中<cache>节点NAME
     * @param key       缓存键
     * @param value     缓存值
     */
    public static void put(String cacheName, Object key, Object value) {
        getOrAddCache(cacheName).put(new Element(key, value));
    }

    /**
     * 获取某个缓存名称中的某个缓存键对应的缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @param <T>
     * @return 返回缓存名称对应缓存值
     */
    public static <T> T get(String cacheName, Object key) {
        Element element = getOrAddCache(cacheName).get(key);
        if (element == null) {
            return null;
        } else {
            Object objectValue = element.getObjectValue();
            return (T) objectValue;
        }
    }

    /**
     * 获取某个缓存名称中的所有缓存键
     *
     * @param cacheName 缓存名称
     * @return 返回该缓存中的所有缓存键
     */
    public static List getKeys(String cacheName) {
        return getOrAddCache(cacheName).getKeys();
    }

    /**
     * 删除某个缓存名称中的缓存键对应的缓存
     *
     * @param cacheName
     * @param key
     */
    public static void remove(String cacheName, Object key) {
        getOrAddCache(cacheName).remove(key);
    }

    /**
     * 删除某个缓存名称下的所有缓存
     *
     * @param cacheName 缓存名称
     */
    public static void removeAll(String cacheName) {
        getOrAddCache(cacheName).removeAll();
    }

    private static Cache getOrAddCache(String cacheName) {
        CacheManager cacheManager = SpringContextUtil.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            synchronized (LOCKER) {
                cache = cacheManager.getCache(cacheName);
                if (cache == null) {
                    cacheManager.addCacheIfAbsent(cacheName);
                    cache = cacheManager.getCache(cacheName);
                }
            }
        }
        return cache;
    }
}


