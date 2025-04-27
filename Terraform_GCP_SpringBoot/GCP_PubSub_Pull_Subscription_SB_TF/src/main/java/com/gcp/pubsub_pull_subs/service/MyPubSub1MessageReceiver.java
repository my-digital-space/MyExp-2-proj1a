package com.gcp.pubsub_pull_subs.service;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPubSub1MessageReceiver implements MessageReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MyPubSub1MessageReceiver.class);

    @Override
    public void receiveMessage(PubsubMessage pubsubMessage,
                               AckReplyConsumer ackReplyConsumer) {
        String payload = pubsubMessage.getData().toStringUtf8();
        logger.debug("Pub/Sub message received: RAW: {}", payload);
        processMessage(payload, ackReplyConsumer);
    }

    private void processMessage(String payload, AckReplyConsumer ackReplyConsumer) {
        // then use ObjectMapper to map the payload to our Class
        // then acknowledge the message
        ackReplyConsumer.ack();
    }
}
