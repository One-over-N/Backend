package com.oneovern.domain.party.service;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.repository.MemberRepository;
import com.oneovern.domain.ott.entity.OttPlan;
import com.oneovern.domain.ott.repository.OttPlanRepository;
import com.oneovern.domain.party.dto.PartyReqDto;
import com.oneovern.domain.party.entity.Party;
import com.oneovern.domain.party.enums.PartyStatus;
import com.oneovern.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartyService {

    private final PartyRepository partyRepository;
    private final OttPlanRepository ottPlanRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createParty(Long planId, PartyReqDto dto) {
        OttPlan ottPlan = ottPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 요금제입니다."));

        Member leader = memberRepository.findById(dto.getLeaderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Party party = Party.builder()
                .partyName(dto.getPartyName())
                .ottAccountId(dto.getOttAccountId())
                .ottAccountPassword(dto.getOttAccountPassword())
                .bank(dto.getBank())
                .bankAccount(dto.getBankAccount())
                .partyStatus(PartyStatus.RECRUITING)
                .ottPlan(ottPlan)
                .leader(leader)
                .build();

        Party savedParty = partyRepository.save(party);
        return savedParty.getId();
    }
}