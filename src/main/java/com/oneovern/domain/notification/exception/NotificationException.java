package com.oneovern.domain.notification.exception;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.exception.ProjectException;

public class NotificationException extends ProjectException {
    public NotificationException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
