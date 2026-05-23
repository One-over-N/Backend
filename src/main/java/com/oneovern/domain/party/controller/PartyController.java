package com.oneovern.domain.party.controller;

import com.oneovern.domain.party.dto.PartyResDto;
import com.oneovern.domain.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @GetMapping("/api/parties/joined")
    public PartyResDto.JoinedPartyListResponse getJoinedParties(
            @RequestParam Long memberId
    ) {
        return partyService.getJoinedParties(memberId);
    }
}