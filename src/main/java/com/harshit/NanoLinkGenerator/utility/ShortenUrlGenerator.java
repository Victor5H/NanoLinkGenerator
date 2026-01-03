package com.harshit.NanoLinkGenerator.utility;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShortenUrlGenerator {
    private final UniqueIdGen uniqueIdGen;
    private final Base62Encoder base62Encoder;

    public String getShortenUrl(){
        return base62Encoder.getEncoded(uniqueIdGen.getUniqueId()).substring(0,7);
    }
}
