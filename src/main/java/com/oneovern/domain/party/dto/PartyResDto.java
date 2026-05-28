package com.oneovern.domain.party.dto;

import com.oneovern.domain.party.enums.PartyStatus;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class PartyResDto {

    @Getter
    @Builder
    public static class PartyInquiryDto {
        private Long partyId;
        private String partyName;
        private String planName;
        private Integer leaderReliability;
        private Integer currentMemberCount;
        private Integer maxPeople;
        private Integer monthlyPrice;
        private PartyStatus partyStatus;
    }

    @Getter
    @Builder
    public static class PartyDetailDto {
        private String partyName;
        private String planName;
        private Integer maxPeople;
        private Integer currentMemberCount;
        private Integer monthlyPrice;
        private List<PartyMemberInfoDto> partyMembers;
    }

    @Getter
    @Builder
    public static class PartyMemberInfoDto {
        private String nickname;
        private Integer reliabilityScore;
        private boolean isLeader;
    }
}