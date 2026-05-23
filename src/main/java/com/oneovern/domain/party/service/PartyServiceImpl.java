package com.oneovern.domain.party.service;

import com.oneovern.domain.party.dto.PartyResDto;
import com.oneovern.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;

    @Override
    public PartyResDto.JoinedPartyListResponse getJoinedParties(Long memberId) {
        return null;
    }
}