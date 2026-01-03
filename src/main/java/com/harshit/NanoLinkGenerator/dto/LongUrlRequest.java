package com.harshit.NanoLinkGenerator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LongUrlRequest {
    @NotBlank(message ="no url")
    private String url;
}
