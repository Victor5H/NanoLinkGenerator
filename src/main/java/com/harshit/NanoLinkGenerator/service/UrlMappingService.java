package com.harshit.NanoLinkGenerator.service;

import com.harshit.NanoLinkGenerator.mapper.UrlMappingMapper;
import com.harshit.NanoLinkGenerator.model.KafkaPushMsg;
import com.harshit.NanoLinkGenerator.model.UrlMapping;
import com.harshit.NanoLinkGenerator.repo.UrlMappingRepo;
import com.harshit.NanoLinkGenerator.utility.FallBackUrlGen;
import com.harshit.NanoLinkGenerator.utility.ShortenUrlGenerator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class UrlMappingService {
    static {
        KAFKATOPICNAME = "nanoLinkTopic";
        logger= LoggerFactory.getLogger(UrlMappingService.class);
    }
    private static final String KAFKATOPICNAME;
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final UrlMappingRepo urlRepo;
    private final ShortenUrlGenerator shortenUrlGenerator;
    private final UrlMappingMapper urlMappingMapper;
    private final FallBackUrlGen fallBackUrlGen;
    private final RedisUrlService redisUrlService;
    private static final Logger logger;

    private Optional<String> getRedisResult(CompletableFuture<String> future){
        while (true){
            if(future.isDone()){
                String s = future.getNow(null);
                if(s ==null) return Optional.empty();
                return Optional.of(s);
            }
        }
    }
    public String getUrl(String longUrl) {
        String shortURL="";
//        checking in cache first in async
        CompletableFuture<String> futureRedisCheck = redisUrlService.checkForLongUrl(longUrl);
//        checking in db
        Optional<UrlMapping> findInDb = urlRepo.findByLongUrl(longUrl);
//        passing to method to check for presence
        Optional<String> redisResult = getRedisResult(futureRedisCheck);
        if(redisResult.isPresent()){
            logger.info("cache hit, returning short url");
            return redisResult.get();
        }
        else if (findInDb.isEmpty()) {
            logger.info("no existing short url, generating short url");
            shortURL = shortenUrlGenerator.getShortenUrl();
            Optional<UrlMapping> checkShortUrl = urlRepo.findByShortUrl(shortURL);
            if(checkShortUrl.isPresent()){
                //fallBackLogic
                logger.info("newly generated short url found in DB");
                shortURL = fallBackUrlGen.getNewUrl(shortURL);
                logger.info("fallback short url generated");
            }
            logger.info("unique short url generated");
            redisUrlService.putNew(longUrl,shortURL);
            String json = new ObjectMapper().writeValueAsString(new KafkaPushMsg(longUrl,shortURL));
            kafkaTemplate.send(KAFKATOPICNAME,shortURL,json);
            logger.info("pushed into kafka");
            return shortURL;

        }
        logger.info("existing short url found in DB");
        return urlMappingMapper.urlToUrlDto(findInDb.get()).getShortUrl();
    }
}
