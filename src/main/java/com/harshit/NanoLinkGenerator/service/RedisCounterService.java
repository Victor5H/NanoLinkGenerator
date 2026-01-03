package com.harshit.NanoLinkGenerator.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RedisCounterService {
    private static final String KEY = "uidCounter";
    private RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisCounterService.class);

    @Transactional
    public BigInteger getCounterAndIncrement() {
        String value = (String) redisTemplate.opsForValue().get(KEY);
        logger.info("{} got from redis",value);
        redisTemplate.opsForValue().increment(KEY, 1L);
        String temp =  Optional.ofNullable(value).map(String::valueOf).orElse("0");
        return new BigInteger(temp);
    }
}
