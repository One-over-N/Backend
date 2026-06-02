package com.oneovern.domain.ott.exception;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.exception.ProjectException;

public class OttException extends ProjectException {
    public OttException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}