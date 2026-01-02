package com.harshit.NanoLinkWriter.utility;

import com.harshit.NanoLinkWriter.service.RedisCounterService;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class FallBackUrlGen {
    private final RedisCounterService redisCounterService;
    private final Base62Encoder base62Encoder;
    FallBackUrlGen(RedisCounterService redisCounterService,Base62Encoder base62Encoder){
        this.redisCounterService = redisCounterService;
        this.base62Encoder =base62Encoder;
    }
    public String getNewUrl(String shortUrl){
        BigInteger num = redisCounterService.getCounterAndIncrement();
        String encoded =base62Encoder.getEncoded(num);
        return shortUrl + encoded;
    }
}
