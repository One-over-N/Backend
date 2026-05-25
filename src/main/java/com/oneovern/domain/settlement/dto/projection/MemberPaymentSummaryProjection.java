package com.oneovern.domain.settlement.dto.projection;

public interface MemberPaymentSummaryProjection {
    Integer getCurrentMonthBillingAmount();
    Integer getSavedAmount();
    Integer getCompletedPaymentCount();
    Integer getTotalPaymentCount();
}
