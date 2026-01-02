package com.harshit.NanoLinkGenerator.mapper;

import com.harshit.NanoLinkGenerator.dto.UrlMappingDto;
import com.harshit.NanoLinkGenerator.model.UrlMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlMappingMapper{
    UrlMapping urlDtoToUrl(UrlMappingDto dto);
    UrlMappingDto urlToUrlDto(UrlMapping urlMapping);
}
