package com.abg.amp.configuration;

import com.abg.amp.constants.Constants;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {
    public Cache<String,Object> cache;
    public void CacheAccessUtil(String key, Object value, long currentTime) {
        cache =  Caffeine.newBuilder()
                .maximumSize(50)
                .expireAfter(new Expiry<String, Object>() {
                    public long expireAfterCreate(String key, Object value, long currentTime) {
                        return TimeUnit.SECONDS.toNanos(Constants.time);
                    }
                    public long expireAfterUpdate(String key, Object value, long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                    public long expireAfterRead(String key, Object value, long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                })
                .build();
    }
}
