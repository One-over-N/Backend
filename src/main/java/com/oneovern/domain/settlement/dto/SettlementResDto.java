package com.oneovern.domain.settlement.dto;

import com.oneovern.domain.settlement.enums.PaymentStatus;
import com.oneovern.global.PageResDto;
import lombok.Builder;

import java.time.LocalDate;

public class SettlementResDto {

    @Builder
    public record CurrentMemberPaymentInfo(
            Long partyId,
            Long planId,
            String partyName,
            String ottName,
            String planName,
            PaymentStatus paymentStatus,
            Integer paymentAmount,
            LocalDate targetDate,
            Integer dDay
    ){}
}
