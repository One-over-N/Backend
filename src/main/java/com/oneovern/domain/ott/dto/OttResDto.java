package com.oneovern.domain.ott.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OttResDto {
    private Long id;
    private String serviceName;
    private String imageUrl;
}