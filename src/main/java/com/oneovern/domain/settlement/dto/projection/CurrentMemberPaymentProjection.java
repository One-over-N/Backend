package com.oneovern.domain.settlement.dto.projection;

import com.oneovern.domain.settlement.enums.PaymentStatus;

import java.time.LocalDate;

public interface CurrentMemberPaymentProjection {
    Long getMemberPaymentId();
    String getPartyName();
    String getOttName();
    String getPlanName();
    PaymentStatus getPaymentStatus();
    Integer getPaymentAmount();
    LocalDate getTargetDate();
}
