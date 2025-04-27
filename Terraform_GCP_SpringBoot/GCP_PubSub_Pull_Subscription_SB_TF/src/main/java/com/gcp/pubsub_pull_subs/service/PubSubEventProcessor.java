package com.gcp.pubsub_pull_subs.service;

import com.gcp.pubsub_pull_subs.config.AppConfig;
import com.gcp.pubsub_pull_subs.config.PubSubConfig;
import com.google.api.core.ApiService;
import com.google.api.gax.core.ExecutorProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;
import com.google.cloud.pubsub.v1.MessageReceiver;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.pubsub.v1.ProjectSubscriptionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PubSubEventProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PubSubEventProcessor.class);

    private final CredentialsService credentialsService;
    private final AppConfig appConfig;
    private final MyPubSub1MessageReceiver myPubSub1MessageReceiver;
    private final MyPubSub2MessageReceiver myPubSub2MessageReceiver;

    public PubSubEventProcessor(CredentialsService credentialsService,
                                AppConfig appConfig,
                                MyPubSub1MessageReceiver myPubSub1MessageReceiver,
                                MyPubSub2MessageReceiver myPubSub2MessageReceiver) {
        this.credentialsService = credentialsService;
        this.appConfig = appConfig;
        this.myPubSub1MessageReceiver = myPubSub1MessageReceiver;
        this.myPubSub2MessageReceiver = myPubSub2MessageReceiver;
    }

    public void startPubSub1Subscriber() {
        startSubscriberWithConfig(appConfig.getMyPubSubConfig1(),
                "My Pub/Sub 1", myPubSub1MessageReceiver);
    }

    public void startPubSub2Subscriber() {
        startSubscriberWithConfig(appConfig.getMyPubSubConfig2(),
                "My Pub/Sub 2", myPubSub2MessageReceiver);
    }

    private void startSubscriberWithConfig(PubSubConfig pubSubConfig,
                                           String logPrefix, MessageReceiver receiver) {
        String projectId = pubSubConfig.getProjectId();
        String subscriptionName = pubSubConfig.getSubscriptionName();
        String topicName = pubSubConfig.getTopicName();
        //logs
        logger.debug("log:{} projectId:{} subscriptionName:{} topicName:{}",
                logPrefix, projectId, subscriptionName, topicName);
        ProjectSubscriptionName subscription = ProjectSubscriptionName.of(projectId, subscriptionName);

        // configure the executor with the specific thread count
        ExecutorProvider executorProvider = InstantiatingExecutorProvider
                .newBuilder()
                .setExecutorThreadCount(pubSubConfig.getThreadCount())
                .build();
        Subscriber.Builder builder = Subscriber
                .newBuilder(subscription, receiver)
                .setExecutorProvider(executorProvider)
                .setCredentialsProvider(credentialsService.buildCredentialsProvider());
        Subscriber subscriber = builder.build();
        subscriber.addListener(new Subscriber.Listener() {
            @Override
            public void failed(ApiService.State from, Throwable failure) {
                logger.error("{} Exception in processing: message:{}",
                        logPrefix, failure.getMessage(), failure);
            }
        }, MoreExecutors.directExecutor());

        // start the subscriber asynchronously
        subscriber.startAsync().awaitRunning();
    }
}
