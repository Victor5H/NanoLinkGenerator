package com.harshit.NanoLinkWriter;

import com.harshit.NanoLinkWriter.utility.Base62Encoder;
import io.netty.handler.codec.base64.Base64Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Base64;
import java.util.UUID;

//@SpringBootTest
class NanoLinkWriterApplicationTests {

	@Test
	void contextLoads() {
        for (int i = 0; i < 5; i++) {
            String uid = UUID.randomUUID().toString().replace("-","");
            BigInteger b = new BigInteger(uid,16);
            String symbols = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            BigInteger base = BigInteger.valueOf(symbols.length());
            StringBuilder sb = new StringBuilder();
            while (b.compareTo(BigInteger.ZERO)>0){
                BigInteger [] ar =b.divideAndRemainder(base);
                b = ar[0];
                sb.insert(0,symbols.charAt(ar[1].intValue()));
            }
            System.out.println(sb.substring(sb.length()-7,sb.length()));
            System.out.println(sb);
        }

	}

}
