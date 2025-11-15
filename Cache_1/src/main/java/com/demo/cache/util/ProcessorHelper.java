package com.demo.cache.util;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProcessorHelper {

    private final Cache eventsCache;

    public ProcessorHelper(CacheManager cacheManager) {
        this.eventsCache = cacheManager.getCache("myEventsCache");
    }

    public void putInCache(String key, String value) {
        eventsCache.put(key, value);
    }

    public boolean isPresentInCache(String value) {
        // printCache();
        return eventsCache.get(value) != null &&
                eventsCache.get(value,String.class).equals("COMPLETED");
    }

    public void printCache() {
        if (eventsCache instanceof ConcurrentMapCache concurrentMapCache) {
            System.out.println("\n\n");
            Map<Object, Object> nativeMap = concurrentMapCache.getNativeCache(); // underlying map
            nativeMap.forEach((k, v) -> System.out.println(k + " = " + v));
            System.out.println("\n\n");
        } else if (eventsCache instanceof CaffeineCache caffeineCache) {
            var nativeCache = caffeineCache.getNativeCache().asMap(); // Get underlying cache
            nativeCache.forEach((k, v) ->
                    System.out.println("Key: " + k + ", Value: " + v)
            );
        }
        else {
            System.out.println("Cache implementation does not support direct printing.");
        }
    }

    public Map<Object, Object> getCacheContents() {
        if (eventsCache instanceof ConcurrentMapCache concurrentMapCache) {
            return concurrentMapCache.getNativeCache();
        }  else if (eventsCache instanceof CaffeineCache caffeineCache) {
            return caffeineCache.getNativeCache().asMap();
        } else {
            System.out.println("Cache implementation does not support direct Map conversion.");
        }
        return null;
    }

}
