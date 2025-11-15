package com.demo.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "cache") // this will load all of our cache configurations for all the caches
public class CacheProperties {

    private Defaults defaults = new Defaults();
    private Map<String, CacheSpec> caches = new HashMap<>();

    @Setter
    @Getter
    public static class Defaults {
        private Duration ttl = Duration.ofMinutes(10);
        private long size = 10_000; // number of cache entries
    }

    @Setter
    @Getter
    public static class CacheSpec {
        private Duration ttl;
        private Long size;

        public Duration resolveTtl(Duration defaultTtl) {
            return Objects.requireNonNullElse(ttl, defaultTtl);
        }

        public long resolveSize(long defaultSize) {
            return Objects.requireNonNullElse(size, defaultSize);
        }
    }

}
