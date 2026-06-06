package com.oneovern.domain.party.dto;

import com.oneovern.domain.party.enums.PartyStatus;

public interface PartyDetailProjection {
    Long getPartyId();
    String getPartyName();
    String getPlanName();
    Integer getLeaderReliability();
    Integer getMemberCount();
    String getPartyStatus();
    Integer getMonthlyPrice();
    Integer getMaxMembers();  // 추가
}