package com.oneovern.domain.settlement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SettlementStatus {
    PENDING("대기중"),
    COMPLETED("정산 완료");

    private final String description;

}
