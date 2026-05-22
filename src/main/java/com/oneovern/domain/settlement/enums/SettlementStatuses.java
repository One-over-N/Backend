package com.oneovern.domain.settlement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SettlementStatuses {
    PENDING("대기중"),
    CONFIRMED("정산 완료");

    private final String discription;

}
