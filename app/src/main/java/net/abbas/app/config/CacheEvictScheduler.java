package net.abbas.app.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CacheEvictScheduler {
    @CacheEvict("apiCache15min")
    @Scheduled(fixedDelay =300000 )
    public void cacheEvict15Min(){
        System.out.println(LocalDateTime.now().toLocalDate() + " - " +
                LocalDateTime.now().toLocalTime() + "API 15 Min Cache Evicted");
    }

    @CacheEvict("apiCache30min")
    @Scheduled(fixedDelay =600000 )
    public void cacheEvict30Min(){
        System.out.println(LocalDateTime.now().toLocalDate() + " - " +
                LocalDateTime.now().toLocalTime() + "API 30 Min Cache Evicted");
    }
}
