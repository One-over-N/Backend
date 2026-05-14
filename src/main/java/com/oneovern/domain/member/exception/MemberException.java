package com.oneovern.domain.member.exception;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.exception.ProjectException;

public class MemberException extends ProjectException {
    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
