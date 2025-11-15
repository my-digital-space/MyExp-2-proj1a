package com.demo.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class CacheConfig {
    private final CacheProperties props;

    public CacheConfig(CacheProperties props) {
        this.props = props;
    }

    @Bean
    public CacheManager cacheManager() {
        var manager = new CaffeineCacheManager();
        var defaults = props.getDefaults(); // default builder for unspecified caches
        manager.setCaffeine(createCaffeineBuilder(defaults.getTtl(), defaults.getSize()));

        props.getCaches().forEach(
                (name, spec) -> {
                    Duration ttl = spec.resolveTtl(defaults.getTtl());
                    long size = spec.resolveSize(defaults.getSize());
                    var nativeCache = createCaffeineBuilder(ttl, size).build();
                    manager.registerCustomCache(name, nativeCache);
                }
        );

        return manager;
    }

    private Caffeine<Object, Object> createCaffeineBuilder(Duration ttl, long size) {
        return Caffeine.newBuilder().expireAfterWrite(ttl).maximumSize(size);
    }
}
