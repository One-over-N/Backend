package com.oneovern.domain.member.exception.code;

import com.oneovern.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    // 유저 조회
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MEMBER404_1",
            "사용자를 찾을 수 없습니다."),

    // 회원 가입
    ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER400_1", "이미 존재하는 회원입니다."),

    // 로그인
    INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER400_2", "비밀번호가 틀렸습니다.");



    private final HttpStatus status;
    private final String code;
    private final String message;
}
