package com.oneovern.domain.notification.exception.code;

import com.oneovern.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationSuccessCode implements BaseSuccessCode {

    GET_NOTIFICATION_LIST(HttpStatus.OK, "NOTIFICATION200_1", "성공적으로 알림 목록이 조회되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
