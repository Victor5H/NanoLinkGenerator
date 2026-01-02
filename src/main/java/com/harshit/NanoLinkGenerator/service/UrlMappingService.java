package com.harshit.NanoLinkGenerator.service;

import com.harshit.NanoLinkGenerator.mapper.UrlMappingMapper;
import com.harshit.NanoLinkGenerator.model.KafkaPushMsg;
import com.harshit.NanoLinkGenerator.model.UrlMapping;
import com.harshit.NanoLinkGenerator.repo.UrlMappingRepo;
import com.harshit.NanoLinkGenerator.utility.FallBackUrlGen;
import com.harshit.NanoLinkGenerator.utility.ShortenUrlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UrlMappingService {
    private final String kafkaTopicName = "nanoLinkTopic";
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
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
        Optional<UrlMapping> findInDb = urlRepo.findByLongUrl(longUrl);
        Optional<String> redisResult = getRedisResult(future);
        if(redisResult.isPresent()){
            logger.info("got from redis");
            return redisResult.get();
        }
        else if (findInDb.isEmpty()) {
            logger.info("no existing short url");
            String shortenURL = shortenUrlGenerator.getShortenUrl();
            Optional<UrlMapping> check = urlRepo.findByShortUrl(shortenURL);
            if(check.isEmpty()){
                logger.info("unique short url generated");
                redisUrlService.putNew(longUrl,shortenURL);
                //            kafke me dalna padega
                String json = new ObjectMapper().writeValueAsString(new KafkaPushMsg(longUrl,shortenURL));
                kafkaTemplate.send(kafkaTopicName,shortenURL,json);
                logger.info("pushed into kafka");
                return shortenURL;
            }
//            fallBackLogic
            logger.info("fallback short url generated");
            return fallBackUrlGen.getNewUrl(shortenURL);

        }
        logger.info("existing short url found");
        return urlMappingMapper.urlToUrlDto(findInDb.get()).getShortUrl();
    }
}
