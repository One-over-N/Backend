package com.oneovern.domain.notification.exception.code;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements BaseErrorCode {

    NO_NOTIFICATION_TO_READ(HttpStatus.BAD_REQUEST, "NOTIFICATION400_1", "읽음 처리할 알림이 존재하지 않습니다."),
    NOTIFICATION_NOT_OWNED(HttpStatus.FORBIDDEN, "NOTIFICATION403_1", "해당 알림에 대한 접근 권한이 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
