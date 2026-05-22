package com.oneovern.domain.notification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationTypes {
    PAYMENT_REQUEST("정산 요청"),
    JOIN_REQUEST("가입 요청"),
    JOIN_APPROVED("가입 승인");

    private final String discription;
}
