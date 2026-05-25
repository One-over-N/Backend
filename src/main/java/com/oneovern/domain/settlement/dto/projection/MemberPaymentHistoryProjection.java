package com.oneovern.domain.settlement.dto.projection;

import java.time.LocalDateTime;

public interface MemberPaymentHistoryProjection {

    Long getMemberPaymentId();
    String getPartyName();
    String getOttName();
    String getPlanName();
    Integer getPaymentAmount();
    LocalDateTime getPaidAt();
}
