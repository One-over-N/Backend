package com.oneovern.domain.member.exception.code;

import com.oneovern.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    //회원가입
    SIGNED_UP(HttpStatus.OK, "MEMBER200_1", "성공적으로 회원가입 되었습니다."),

    //로그인
    LOGINED(HttpStatus.OK, "MEMBER200_2", "성공적으로 로그인 되었습니다."),

    //로그아웃
    LOGGED_OUT(HttpStatus.OK,"MEMBER200_3" , "성공적으로 로그아웃 되었습니다."),

    //마이페이지
    MEMBER_FOUND(HttpStatus.OK, "MEMBER200_4", "성공적으로 회원 정보를 조회했습니다.");
    
    private final HttpStatus status;
    private final String code;
    private final String message;
}
