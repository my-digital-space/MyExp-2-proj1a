package com.demo.cache.controller;

import com.demo.cache.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {
    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping(value = "/test/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCacheResponse(@PathVariable("eventId") String eventId) {
        return cacheService.processRequest(eventId);
    }
}
