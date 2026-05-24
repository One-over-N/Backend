package com.oneovern.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDto {

    //회원가입
    @Builder
    public record Join(
            Long userId,
            LocalDateTime createdAt
    ){}

    //로그인
    @Builder
    public record Login(
            Long userId,
            String accessToken,
            String refreshToken
    ){}

    //마이페이지 화면 상단 회원 정보
    @Builder
    public record MyPage(
            String nickname,
            String email,
            int reliabilityScore
    ){}

}
