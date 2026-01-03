package com.harshit.NanoLinkGenerator.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomUUIDGenTest {
    @Autowired
    CustomUUIDGen customUUIDGen;
    @Test
    public void test(){
        Assertions.assertNotEquals(customUUIDGen.getUniqueId(),customUUIDGen.getUniqueId());
    }
}
