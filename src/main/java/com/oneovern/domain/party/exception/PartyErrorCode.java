package com.oneovern.domain.party.exception;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PartyErrorCode implements BaseErrorCode {

    PARTY_NOT_FOUND(HttpStatus.NOT_FOUND, "PARTY404_1", "존재하지 않는 파티입니다."),
    OTT_PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "PARTY404_2", "존재하지 않는 요금제입니다."),
    JOIN_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "PARTY404_3", "존재하지 않는 신청 정보입니다."),

    DUPLICATE_JOIN_REQUEST(HttpStatus.BAD_REQUEST, "PARTY400_1", "이미 가입 신청을 완료했거나 완료된 파티입니다."),
    PARTY_FULL(HttpStatus.BAD_REQUEST, "PARTY400_2", "파티 정원이 초과되어 더 이상 신청할 수 없습니다."),
    NOT_PARTY_LEADER(HttpStatus.FORBIDDEN, "PARTY403_1", "해당 파티의 방장만 가입 신청을 처리할 수 있습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}