package com.oneovern.domain.ott.converter;

import com.oneovern.domain.ott.dto.OttResDto;
import com.oneovern.domain.ott.dto.OttPlanResDto;
import com.oneovern.domain.ott.entity.Ott;
import com.oneovern.domain.ott.entity.OttPlan;
import java.util.List;

public class OttConverter {

    public static OttResDto toOttResDto(Ott ott) {
        return OttResDto.builder()
                .id(ott.getId())
                .serviceName(ott.getServiceName())
                .imageUrl(ott.getImageUrl())
                .build();
    }

    public static List<OttResDto> toOttResDtoList(List<Ott> ottList) {
        return ottList.stream()
                .map(OttConverter::toOttResDto)
                .toList();
    }

    public static OttPlanResDto toOttPlanResDto(OttPlan ottPlan) {
        return OttPlanResDto.builder()
                .id(ottPlan.getId())
                .planName(ottPlan.getPlanName())
                .monthlyPrice(ottPlan.getMonthlyPrice())
                .maxMembers(ottPlan.getMaxMembers())
                .createdAt(ottPlan.getCreatedAt())
                .updatedAt(ottPlan.getUpdatedAt())
                .build();
    }

    public static List<OttPlanResDto> toOttPlanResDtoList(List<OttPlan> ottPlanList) {
        return ottPlanList.stream()
                .map(OttConverter::toOttPlanResDto)
                .toList();
    }
}