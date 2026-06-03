package com.oneovern.domain.notification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    PAYMENT_REQUEST("정산 요청"),
    JOIN_REQUEST("파티 가입 요청"),
    JOIN_APPROVED("파티 가입 승인"),
    JOIN_REJECTED("파티 가입 거절");

    private final String description;
}
