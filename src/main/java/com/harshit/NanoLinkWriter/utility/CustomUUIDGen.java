package com.harshit.NanoLinkWriter.utility;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.UUID;
@Primary
@Component
public class CustomUUIDGen implements UniqueIdGen{
    @Override
    public BigInteger getUniqueId() {
        String uid = UUID.randomUUID().toString().replace("-","");
        return new BigInteger(uid,16);
    }
}
