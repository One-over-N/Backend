package com.oneovern.domain.party.dto;

public interface PartyMemberProjection {
    String getNickname();
    Integer getReliabilityScore();
    Integer getIsLeader();
}