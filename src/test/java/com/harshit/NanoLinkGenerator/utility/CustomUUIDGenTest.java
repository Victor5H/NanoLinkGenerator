package com.harshit.NanoLinkGenerator.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
 class CustomUUIDGenTest {
    @Autowired
    CustomUUIDGen customUUIDGen;
    @Test
     void test(){
        Assertions.assertNotEquals(customUUIDGen.getUniqueId(),customUUIDGen.getUniqueId());
    }
}
