package com.oneovern.domain.notification.exception.code;

import com.oneovern.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationSuccessCode implements BaseSuccessCode {

    GET_NOTIFICATION_LIST(HttpStatus.OK, "NOTIFICATION200_1", "성공적으로 알림 목록이 조회되었습니다."),
    READ_NOTIFICATION(HttpStatus.OK, "NOTIFICATION200_2", "성공적으로 알림이 읽음 처리되었습니다."),
    GET_UNREAD_NOTIFICATION_COUNT(HttpStatus.OK, "NOTIFICATION200_3", "성공적으로 읽지 않은 알림의 개수가 조회되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
