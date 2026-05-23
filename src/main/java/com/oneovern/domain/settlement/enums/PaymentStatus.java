package com.oneovern.domain.settlement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    PAID("납부 완료"),
    UNPAID("대기중");

    private final String description;
}
