package com.oneovern.domain.settlement.exception;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.exception.ProjectException;

public class SettlementException extends ProjectException {
    public SettlementException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
