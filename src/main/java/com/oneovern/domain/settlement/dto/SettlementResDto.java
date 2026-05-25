package com.oneovern.domain.settlement.dto;

import com.oneovern.domain.settlement.enums.PaymentStatus;
import lombok.Builder;

import java.time.LocalDate;

public class SettlementResDto {

    @Builder
    public record CurrentMemberPaymentInfo(
            Long memberPaymentId,
            String partyName,
            String ottName,
            String planName,
            PaymentStatus paymentStatus,
            Integer paymentAmount,
            LocalDate targetDate,
            Integer dDay
    ){}

    @Builder
    public record MemberPaymentSummary(
            Integer currentMonthBillingAmount,
            Integer savedAmount,
            Integer completedPaymentCount,
            Integer totalPaymentCount
    ){}
}
