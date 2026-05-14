package com.oneovern.domain.party.exception;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.exception.ProjectException;

public class PartyException extends ProjectException {
    public PartyException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
