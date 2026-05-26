package com.oneovern.domain.ott.dto;

import com.oneovern.domain.ott.entity.OttPlan;
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

    public static OttPlanResDto from(OttPlan ottPlan) {
        return OttPlanResDto.builder()
                .id(ottPlan.getId())
                .planName(ottPlan.getPlanName())
                .monthlyPrice(ottPlan.getMonthlyPrice())
                .maxMembers(ottPlan.getMaxMembers())
                .createdAt(ottPlan.getCreatedAt())
                .updatedAt(ottPlan.getUpdatedAt())
                .build();
    }
}