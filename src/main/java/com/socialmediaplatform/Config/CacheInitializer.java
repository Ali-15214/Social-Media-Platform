package com.socialmediaplatform.Config;

import jakarta.annotation.PostConstruct;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;


@Component
public class CacheInitializer {

    private final CacheManager cacheManager;

    // Injecting CacheManager to access available caches
    public CacheInitializer(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    // This method will run after Spring context is initialized
    @PostConstruct
    public void init() {
        // Log all available cache names to the console
        System.out.println("Available caches: " + cacheManager.getCacheNames());
    }
}
