package com.harshit.NanoLinkGenerator.utility;

import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class Base62Encoder {
    private final static String symbols = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final BigInteger base = BigInteger.valueOf(62);
    private static final BigInteger zero = BigInteger.ZERO;
    public String getEncoded(BigInteger num){
        StringBuilder sb =new StringBuilder();
        while (num.compareTo(zero)>0){
            BigInteger [] quoAndRem =num.divideAndRemainder(base);
            num = quoAndRem[0];//quotient
            sb.insert(0,symbols.charAt(quoAndRem[1].intValue()));//remainder
        }

        return sb.toString();
    }
}
