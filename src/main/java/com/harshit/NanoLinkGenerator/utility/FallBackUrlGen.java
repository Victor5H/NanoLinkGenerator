package com.harshit.NanoLinkGenerator.utility;

import com.harshit.NanoLinkGenerator.service.RedisCounterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
@AllArgsConstructor
public class FallBackUrlGen {
    private final RedisCounterService redisCounterService;
    private final Base62Encoder base62Encoder;

    public String getNewUrl(String shortUrl){
        BigInteger num = redisCounterService.getCounterAndIncrement();
        String encoded =base62Encoder.getEncoded(num);
        return shortUrl + encoded;
    }
}
