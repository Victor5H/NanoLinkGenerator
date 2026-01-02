package com.harshit.NanoLinkGenerator.utility;

import org.springframework.stereotype.Component;

@Component
public class ShortenUrlGenerator {
    private final UniqueIdGen uniqueIdGen;
    private final Base62Encoder base62Encoder;

    ShortenUrlGenerator(UniqueIdGen uniqueIdGen, Base62Encoder base62Encoder){
        this.base62Encoder = base62Encoder;
        this.uniqueIdGen = uniqueIdGen;
    }
    public String getShortenUrl(){
        return base62Encoder.getEncoded(uniqueIdGen.getUniqueId()).substring(0,7);
    }
}
