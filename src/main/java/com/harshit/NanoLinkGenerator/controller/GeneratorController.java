package com.harshit.NanoLinkGenerator.controller;

import com.harshit.NanoLinkGenerator.dto.LongUrlRequest;
import com.harshit.NanoLinkGenerator.dto.ResponseNanoLink;
import com.harshit.NanoLinkGenerator.service.UrlMappingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:5137")
public class GeneratorController {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorController.class);
    private final UrlMappingService urlService;

    @PostMapping("getNano")

    public ResponseEntity<ResponseNanoLink> post(@RequestBody @Valid LongUrlRequest longUrlRequest){
        String longUrl = longUrlRequest.getUrl();
        logger.info("Long url {}", longUrl);
        String url =urlService.getUrl(longUrl);
        ResponseNanoLink nanoLink = new ResponseNanoLink(url);
        return ResponseEntity.ok(nanoLink);
    }
}
