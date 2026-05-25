package com.oneovern.domain.settlement.exception.code;

import com.oneovern.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SettlementSuccessCode implements BaseSuccessCode {
    GET_CURRENT_MEMBER_PAYMENTS(HttpStatus.OK, "SETTLEMENT200_1", "성공적으로 이번 달 정산 현황이 조회되었습니다."),
    GET_MEMBER_PAYMENT_SUMMARY(HttpStatus.OK, "SETTLEMENT200_2", "성공적으로 이번 달 정산 현황 요약이 조회되었습니다."),
    GET_MEMBER_PAYMENT_HISTORY(HttpStatus.OK, "SETTLEMENT200_3", "성공적으로 납부 기록이 조회되었습니다."),
    CHANGE_TO_PAID(HttpStatus.OK, "SETTLEMENT200_4", "성공적으로 납부 상태가 변경되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
