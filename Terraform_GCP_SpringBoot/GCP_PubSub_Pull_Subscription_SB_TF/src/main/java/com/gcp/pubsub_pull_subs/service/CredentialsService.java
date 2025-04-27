package com.gcp.pubsub_pull_subs.service;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CredentialsService {
    private static final Logger logger = LoggerFactory.getLogger(CredentialsService.class);

    public CredentialsProvider buildCredentialsProvider() {
        CredentialsProvider credentialsProvider = null;
        try {
            credentialsProvider = FixedCredentialsProvider.create(GoogleCredentials.getApplicationDefault());
        } catch (IOException ex) {
            logger.error("Error in CredentialsService", ex);
        }
        return credentialsProvider;
    }

}
