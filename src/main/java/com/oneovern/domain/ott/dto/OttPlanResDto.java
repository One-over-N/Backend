package com.oneovern.domain.ott.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class OttPlanResDto {
    private Long id;
    private String planName;
    private Integer monthlyPrice;
    private Integer maxMembers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}