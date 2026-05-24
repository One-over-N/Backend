package com.oneovern.domain.member.converter;

import com.oneovern.domain.member.dto.MemberReqDto;
import com.oneovern.domain.member.dto.MemberResDto;
import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.entity.ReliabilityHistory;
import java.util.List;
import java.util.stream.Collectors;

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
    //최신순 정렬 및 limit 조건
    public static MemberResDto.MyPage toMyPageResDto(Member member) {

        List<MemberResDto.ReliabilityHistoryDto> historyDtoList = member.getReliabilityHistories().stream()
                .sorted((h1, h2) -> h2.getCreatedAt().compareTo(h1.getCreatedAt())) // 최신순(내림차순) 정렬
                .limit(3)
                .map(MemberConverter::toReliabilityHistoryDto)
                .collect(Collectors.toList());

        return MemberResDto.MyPage.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .reliabilityScore(member.getReliabilityScore())
                .reliabilityHistories(historyDtoList) // 정제된 3건 대입
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

    //reliabilityHistory->MemberResDto.ReliabilityHistoryDto
    public static MemberResDto.ReliabilityHistoryDto toReliabilityHistoryDto(ReliabilityHistory history) {
        return MemberResDto.ReliabilityHistoryDto.builder()
                .historyId(history.getId())
                .reason(history.getReason())
                .changeScore(history.getChangeScore())
                .afterScore(history.getAfterScore())
                .createdAt(history.getCreatedAt())
                .build();
    }

}
