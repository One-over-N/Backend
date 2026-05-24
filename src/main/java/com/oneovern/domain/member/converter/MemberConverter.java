package com.oneovern.domain.member.converter;

import com.oneovern.domain.member.dto.MemberReqDto;
import com.oneovern.domain.member.dto.MemberResDto;
import com.oneovern.domain.member.entity.Member;

public class MemberConverter {

    //MemberReqDto.join->member
    public static Member toMember(MemberReqDto.Join dto, String encodedPassword) {
        return Member.builder()
                .email(dto.email())
                .password(encodedPassword)
                .nickname(dto.nickname())
                .build();
    }

    //member->MemberResDto.join
    public static MemberResDto.Join toJoinResDto(Member savedMember) {
        return MemberResDto.Join.builder()
                .userId(savedMember.getId())
                .createdAt(savedMember.getCreatedAt())
                .build();
    }

    //member->MemberResDto.MyPage
    public static MemberResDto.MyPage toMyPageResDto(Member member) {
        return MemberResDto.MyPage.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .reliabilityScore(member.getReliabilityScore())
                .build();
    }

    //member,token->MemberResDto.login
    public static MemberResDto.Login toLoginResDto(Member member, String accessToken, String refreshToken) {
        return MemberResDto.Login.builder()
                .userId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
