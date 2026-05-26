package com.oneovern.domain.ott.dto;

import com.oneovern.domain.ott.entity.Ott;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OttResDto {
    private Long ottServiceId;
    private String serviceName;
    private String imageUrl;

    public static OttResDto from(Ott ott) {
        return OttResDto.builder()
                .ottServiceId(ott.getOttServiceId())
                .serviceName(ott.getServiceName())
                .imageUrl(ott.getImageUrl())
                .build();
    }
}