package com.oneovern.domain.party.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PartyResDto {

    @Getter
    @Builder
    public static class JoinedPartyListResponse {
        private List<JoinedPartyResponse> partyList;
        private Integer totalCount;
    }

    @Getter
    @Builder
    public static class JoinedPartyResponse {
        private Long partyId;
        private String ottAccountID;
        private String ottAccountPW;
        private String bank;
        private String bankAccount;
        private PlanResponse plan;
    }

    @Getter
    @Builder
    public static class PlanResponse {
        private Long planId;
        private String planName;
    }
}