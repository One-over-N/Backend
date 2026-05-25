package com.oneovern.domain.settlement.dto;

import com.oneovern.domain.settlement.enums.PaymentStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Builder
    public record MemberPaymentHistory(
            Long memberPaymentId,
            String partyName,
            String ottName,
            String planName,
            Integer paymentAmount,
            LocalDate paidAt
    ){}

    @Builder
    public record PaymentStatusUpdate(
        Long memberPaymentId,
        PaymentStatus paymentStatus,
        LocalDateTime updatedAt
    ){}
}
