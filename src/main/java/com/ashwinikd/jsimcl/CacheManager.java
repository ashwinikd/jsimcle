package com.ashwinikd.jsimcl;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {
    private static volatile CacheManager instance;
    private static volatile Object monitor = new Object();
    private volatile Map<String, CacheValue> cache = Collections.synchronizedMap(new ConcurrentHashMap<String, CacheValue>());

    private CacheManager() {
    }

    /**
     * @param cacheKey cache key
     * @param value value to store
     */
    public void put(String cacheKey, Object value) {
        CacheValue cv = new CacheValue(value);
        cache.put(cacheKey, cv);
    }

    /**
     * @param cacheKey cache key
     * @param value value to store
     * @param ttl time to live in seconds
     */
    public void put(String cacheKey, Object value, long ttl) {
        CacheValue cv = new CacheValue(value, ttl);
        cache.put(cacheKey, cv);
    }

    /**
     * @param cacheKey cache key
     * @return value
     */
    public Object get(String cacheKey) {
        CacheValue cv = cache.get(cacheKey);
        if(cv == null) return null;
        if(cv.hasExpired()) {
            clear(cacheKey);
            return null;
        }
        return cv.getValue();
    }

    /**
     * @param cacheKey cache key
     */
    public void clear(String cacheKey) {
        cache.remove(cacheKey);
    }

    /**
     * clear the cache
     */
    public void clear() {
        cache.clear();
    }

    /**
     * @return cache manager 
     */
    public static CacheManager getInstance() {
        if (instance == null) {
            synchronized (monitor) {
                if (instance == null) {
                    instance = new CacheManager();
                }
            }
        }
        return instance;
    }
}
