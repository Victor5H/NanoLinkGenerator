package com.harshit.NanoLinkWriter.dto;

import lombok.Data;

@Data
public class UrlMappingDto {
    private Long id;
    private String longUrl;
    private String shortUrl;
}
