package com.gcp.pubsub_pull_subs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gcp.settings")
@EnableCaching
@Getter
@Setter
public class AppConfig {
    private PubSubConfig myPubSubConfig1;
    private PubSubConfig myPubSubConfig2;
}
