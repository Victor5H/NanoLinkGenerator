package com.harshit.NanoLinkGenerator.controller;

import com.harshit.NanoLinkGenerator.service.UrlMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Write {
    Logger logger = LoggerFactory.getLogger(Write.class);
    private final UrlMappingService urlService;
    Write(UrlMappingService checkService){
        this.urlService = checkService;
    }

    @PostMapping("getNano")
    public ResponseEntity<?> post(@RequestBody String longUrl){
        logger.info("Long url {}", longUrl);
        String url =urlService.getUrl(longUrl);
        return ResponseEntity.ok(url);
    }
}
