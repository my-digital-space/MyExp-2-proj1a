package com.gcp.pubsub_pull_subs.config;

import lombok.Data;

@Data
public class PubSubConfig {
    private String projectId;
    private String topicName;
    private String subscriptionName;
    private Integer threadCount;
}
