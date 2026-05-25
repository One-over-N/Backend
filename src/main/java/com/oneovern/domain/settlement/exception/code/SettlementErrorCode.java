package com.oneovern.domain.settlement.exception.code;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SettlementErrorCode implements BaseErrorCode {

    MEMBER_PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "SETTLEMENT404_1", "존재하지 않는 회원 납부 내역입니다."),
    MEMBER_PAYMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "SETTLEMENT403_1", "해당 납부 내역에 대한 접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
