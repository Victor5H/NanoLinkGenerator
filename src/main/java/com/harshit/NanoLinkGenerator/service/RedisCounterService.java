package com.harshit.NanoLinkGenerator.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class RedisCounterService {
    private static final String key= "uidCounter";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    Logger logger = LoggerFactory.getLogger(RedisCounterService.class);

    @Transactional
    public BigInteger getCounterAndIncrement() {
        String value = (String) redisTemplate.opsForValue().get(key);
        logger.info("{} got from redis",value);
        redisTemplate.opsForValue().increment(key, 1L);
        String temp =  Optional.ofNullable(value).map(String::valueOf).orElse("0");
        return new BigInteger(temp);
    }
}
