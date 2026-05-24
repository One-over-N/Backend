package com.oneovern.domain.settlement.controller;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.settlement.dto.SettlementResDto;
import com.oneovern.domain.settlement.exception.code.SettlementSuccessCode;
import com.oneovern.domain.settlement.service.SettlementService;
import com.oneovern.global.ApiResponse;
import com.oneovern.global.PageResDto;
import com.oneovern.global.apiPayload.code.BaseSuccessCode;
import com.oneovern.global.security.annotation.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settlements")
public class SettlementController {
    private final SettlementService settlementService;


    //이번 달 정산
    @GetMapping("/current")
    public ApiResponse<PageResDto<SettlementResDto.CurrentMemberPaymentInfo>> getCurrentMemberPayments(
            @AuthUser Member member,
            @RequestParam(name = "cursor", required = false) Long cursor
    ){
        BaseSuccessCode code= SettlementSuccessCode.GET_CURRENT_MEMBER_PAYMENTS;
        return ApiResponse.onSuccess(code, settlementService.getCurrentMemberPayments(member, cursor));
    }
}
