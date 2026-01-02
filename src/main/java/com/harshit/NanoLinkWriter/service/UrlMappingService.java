package com.harshit.NanoLinkWriter.service;

import com.harshit.NanoLinkWriter.controller.Write;
import com.harshit.NanoLinkWriter.dto.UrlMappingDto;
import com.harshit.NanoLinkWriter.mapper.UrlMappingMapper;
import com.harshit.NanoLinkWriter.model.UrlMapping;
import com.harshit.NanoLinkWriter.repo.UrlMappingRepo;
import com.harshit.NanoLinkWriter.utility.FallBackUrlGen;
import com.harshit.NanoLinkWriter.utility.ShortenUrlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UrlMappingService {
    private final UrlMappingRepo urlRepo;
    private final ShortenUrlGenerator shortenUrlGenerator;
    private final UrlMappingMapper urlMappingMapper;
    @Autowired
    private FallBackUrlGen fallBackUrlGen;
    @Autowired
    private RedisUrlService redisUrlService;
    Logger logger = LoggerFactory.getLogger(UrlMappingService.class);

    UrlMappingService(UrlMappingRepo r, ShortenUrlGenerator shortenUrlGenerator, UrlMappingMapper mapper) {
        urlRepo = r;
        this.shortenUrlGenerator = shortenUrlGenerator;
        this.urlMappingMapper = mapper;
    }
    private Optional<String > getRedisResult(CompletableFuture<String> future){
        logger.info("in get redis result");
        while (true){
            if(future.isDone()){
                String s = future.getNow(null);
                if(s ==null) return Optional.empty();
                return Optional.of(s);
            }
        }
    }
    public String getUrl(String longUrl) {
        CompletableFuture<String> future = redisUrlService.checkForLongUrl(longUrl);
        Optional<UrlMapping> m = urlRepo.findByLongUrl(longUrl);
        Optional<String> redisResult = getRedisResult(future);
        if(redisResult.isPresent()){
            logger.info("got from redis");
            return redisResult.get();
        }
        else if (m.isEmpty()) {
            logger.info("no existing short url");
            String shortenURL = shortenUrlGenerator.getShortenUrl();
            Optional<UrlMapping> check = urlRepo.findByShortUrl(shortenURL);
            if(check.isEmpty()){
                logger.info("unique short url generated");
                redisUrlService.putNew(longUrl,shortenURL);
                //            kafke me dalna padge
                return shortenURL;
            }
//            fallBackLogic
            logger.info("fallback short url generated");
            return fallBackUrlGen.getNewUrl(shortenURL);

        }
        logger.info("existing short url found");
        return urlMappingMapper.urlToUrlDto(m.get()).getShortUrl();
    }
}
