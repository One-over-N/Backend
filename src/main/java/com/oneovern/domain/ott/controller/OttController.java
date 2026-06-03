package com.oneovern.domain.ott.controller;

import com.oneovern.domain.ott.converter.OttConverter;
import com.oneovern.domain.ott.dto.OttResDto;
import com.oneovern.domain.ott.dto.OttPlanResDto;
import com.oneovern.domain.ott.service.OttService;
import com.oneovern.global.ApiResponse;
import com.oneovern.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/otts")
@CrossOrigin(origins = "http://localhost:5173")
public class OttController {

    private final OttService ottService;

    @GetMapping
    public ApiResponse<List<OttResDto>> getOtts() {
        List<OttResDto> resDtoList = OttConverter.toOttResDtoList(ottService.getOttList());
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, resDtoList);
    }

    @GetMapping("/{ottId}/plans")
    public ApiResponse<List<OttPlanResDto>> getOttPlans(@PathVariable Long ottId) {
        List<OttPlanResDto> resDtoList = OttConverter.toOttPlanResDtoList(ottService.getOttPlans(ottId));
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, resDtoList);
    }
}