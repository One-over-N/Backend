package com.oneovern.domain.member.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

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
            int reliabilityScore,
            List<ReliabilityHistoryDto> reliabilityHistories // 최신순 3개 목록이 담길 배열
    ){}

    //마이페이지 '신뢰도 변동 이력' 데이터 상자
    @Builder
    public record ReliabilityHistoryDto(
            Long historyId,
            String reason,  // 변동 사유
            Integer changeScore,
            Integer afterScore,
            LocalDateTime createdAt
    ){}

}
