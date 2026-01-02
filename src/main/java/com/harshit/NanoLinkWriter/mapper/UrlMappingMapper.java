package com.harshit.NanoLinkWriter.mapper;

import com.harshit.NanoLinkWriter.dto.UrlMappingDto;
import com.harshit.NanoLinkWriter.model.UrlMapping;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UrlMappingMapper{
    UrlMapping urlDtoToUrl(UrlMappingDto dto);
    UrlMappingDto urlToUrlDto(UrlMapping urlMapping);
}
