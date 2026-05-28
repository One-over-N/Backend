package com.oneovern.domain.party.controller;

import com.oneovern.domain.party.dto.PartyReqDto;
import com.oneovern.domain.party.service.PartyService;
import com.oneovern.global.apiPayload.ApiResponse;
import com.oneovern.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ott-service")
public class PartyController {

    private final PartyService partyService;

    @PostMapping("/{serviceId}/{planId}")
    public ApiResponse<Long> createParty(
            @PathVariable(name = "serviceId") Long serviceId,
            @PathVariable(name = "planId") Long planId,
            @RequestBody PartyReqDto partyReqDto
    ) {
        Long partyId = partyService.createParty(planId, partyReqDto);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, partyId);
    }
}