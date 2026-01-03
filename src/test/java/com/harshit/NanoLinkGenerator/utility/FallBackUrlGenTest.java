package com.harshit.NanoLinkGenerator.utility;

import com.harshit.NanoLinkGenerator.service.RedisCounterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

 class FallBackUrlGenTest {
    @InjectMocks
    FallBackUrlGen fallBackUrlGen;
    @Mock
    RedisCounterService redisCounterService;
    @Mock
    Base62Encoder encoder = new Base62Encoder();

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void test(){
        Mockito.when(redisCounterService.getCounterAndIncrement()).thenReturn(BigInteger.valueOf(5));
        String demo = "jaskdfhksjd";
        String fallback = fallBackUrlGen.getNewUrl(demo);
        Assertions.assertNotEquals(demo,fallback);
    }

}
