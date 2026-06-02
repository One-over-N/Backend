package com.oneovern.domain.ott.exception;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OttErrorCode implements BaseErrorCode {

    OTT_NOT_FOUND(HttpStatus.NOT_FOUND, "OTT404_1", "OTT 서비스를 찾을 수 없습니다."),
    OTT_PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "OTT404_2", "OTT 플랜을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}