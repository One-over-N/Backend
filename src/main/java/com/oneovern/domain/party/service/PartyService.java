package com.oneovern.domain.party.service;

import com.oneovern.domain.party.dto.PartyResDto;

public interface PartyService {

    PartyResDto.JoinedPartyListResponse getJoinedParties(Long memberId);
}