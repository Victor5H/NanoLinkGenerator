package com.harshit.NanoLinkWriter.service;

import com.harshit.NanoLinkWriter.dto.UrlMappingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUrlService {
    @Autowired
    private RedisTemplate template;
    Logger logger = LoggerFactory.getLogger(RedisUrlService.class);

    @Async
    public CompletableFuture<String> checkForLongUrl(String longUrl){
        try{
            Object o =template.opsForValue().get(longUrl);
            if(o==null) return CompletableFuture.completedFuture(null);
            String res = o.toString();
            return CompletableFuture.completedFuture(res);
        }catch (Exception e){
            return CompletableFuture.failedFuture(e);
        }
    }
    public void putNew(String longUrl, String shortUrl){
        try {
            template.opsForValue().set(longUrl,shortUrl, 10, TimeUnit.SECONDS);
        }
        catch (Exception e){
            logger.error(e.toString());
        }
    }

}
