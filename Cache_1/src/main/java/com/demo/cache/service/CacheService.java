package com.demo.cache.service;

import com.demo.cache.util.ProcessorHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final ProcessorHelper processorHelper;

    public CacheService(ProcessorHelper processorHelper) {
        this.processorHelper = processorHelper;
    }

    public ResponseEntity<String> processRequest(String eventId) {
        // check in cache first
        // if the "eventId" is already present in the cache then skip processing it
        if(processorHelper.isPresentInCache(eventId)) {
            return ResponseEntity.ok("Skipped processing the event!");
        }

        // else, put in the cache now and then process it
        processorHelper.putInCache(eventId,"IN_PROGRESS");

        // mock a long-running task
        longTimeProcess();
        processorHelper.putInCache(eventId,"COMPLETED");

        return ResponseEntity.ok("Event successfully processed!");
    }

    public void longTimeProcess() {
        try {
            Thread.sleep(10_000); // sleep for 10 seconds to mock a long-running task
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
