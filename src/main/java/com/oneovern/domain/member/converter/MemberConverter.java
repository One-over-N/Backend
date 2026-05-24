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
    public static MemberResDto.MyPage toMyPageResDto(Member member, List<ReliabilityHistory> historyList) {
        List<MemberResDto.ReliabilityHistoryDto> historyDtoList = historyList.stream()
                .map(MemberConverter::toReliabilityHistoryDto)
                .toList();

        return MemberResDto.MyPage.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .reliabilityScore(member.getReliabilityScore())
                .reliabilityHistories(historyDtoList) // 이력 주머니 장착
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
