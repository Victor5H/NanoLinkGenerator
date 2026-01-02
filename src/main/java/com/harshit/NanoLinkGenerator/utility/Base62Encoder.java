package com.harshit.NanoLinkGenerator.utility;

import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class Base62Encoder {
    private final static String symbols = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final BigInteger base = BigInteger.valueOf(62);
    private static final BigInteger zero = BigInteger.ZERO;
    private static final int length=7;
    public String getEncoded(BigInteger num){
        StringBuilder sb =new StringBuilder();
        while (num.compareTo(BigInteger.ZERO)>0){
            BigInteger [] ar =num.divideAndRemainder(base);
            num = ar[0];
            sb.insert(0,symbols.charAt(ar[1].intValue()));
        }

        return sb.toString();
    }
}
