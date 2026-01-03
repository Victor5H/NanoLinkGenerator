package com.harshit.NanoLinkGenerator.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

@SpringBootTest
public class Base62EncoderTest {
    @Autowired
    Base62Encoder encoder;
    @ParameterizedTest
    @CsvSource({
            "1,1",
            "2,2",
            "3,3",
            "4,4"
    })
    public void test(int a, int b){
        Assertions.assertEquals(encoder.getEncoded(BigInteger.valueOf(a)),encoder.getEncoded(BigInteger.valueOf(b)));
    }
}
