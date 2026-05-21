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

}
