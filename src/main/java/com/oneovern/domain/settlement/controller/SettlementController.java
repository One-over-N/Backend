package com.oneovern.domain.settlement.controller;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.settlement.dto.SettlementResDto;
import com.oneovern.domain.settlement.enums.PaymentStatus;
import com.oneovern.domain.settlement.exception.code.SettlementSuccessCode;
import com.oneovern.domain.settlement.service.SettlementService;
import com.oneovern.global.ApiResponse;
import com.oneovern.global.PageResDto;
import com.oneovern.global.apiPayload.code.BaseSuccessCode;
import com.oneovern.global.security.annotation.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // 정산 요약
    @GetMapping("/summary")
    public ApiResponse<SettlementResDto.MemberPaymentSummary> getMemberPaymentSummary(
            @AuthUser Member member
    ){
        BaseSuccessCode code=SettlementSuccessCode.GET_MEMBER_PAYMENT_SUMMARY;
        return ApiResponse.onSuccess(code, settlementService.getMemberPaymentSummary(member));
    }

    // 전체 납부 기록
    @GetMapping("/history")
    public ApiResponse<PageResDto<SettlementResDto.MemberPaymentHistory>> getMemberPaymentHistory(
            @AuthUser Member member,
            @RequestParam(name = "cursor", required = false) Long cursor
    ){
        BaseSuccessCode code= SettlementSuccessCode.GET_MEMBER_PAYMENT_HISTORY;
        return ApiResponse.onSuccess(code, settlementService.getMemberPaymentHistory(member, cursor));
    }

    // 납부 처리
    @PatchMapping("/{member-payment-id}/status")
    public ApiResponse<SettlementResDto.PaymentStatusUpdate> updateMemberPaymentStatus(
            @AuthUser Member member,
            @PathVariable("member-payment-id") Long memberPaymentId,
            @RequestBody PaymentStatus paymentStatus
    ){
        BaseSuccessCode code=SettlementSuccessCode.CHANGE_TO_PAID;
        return ApiResponse.onSuccess(code, settlementService.updateMemberPaymentStatus(member, memberPaymentId, paymentStatus));
    }

}
