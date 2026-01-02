package com.harshit.NanoLinkGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaPushMsg {
    private String longUrl;
    private String shortUrl;
}
