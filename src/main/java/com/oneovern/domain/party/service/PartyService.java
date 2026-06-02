package com.oneovern.domain.party.service;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.repository.MemberRepository;
import com.oneovern.domain.ott.entity.OttPlan;
import com.oneovern.domain.ott.repository.OttPlanRepository;
import com.oneovern.domain.party.dto.PartyDetailProjection;
import com.oneovern.domain.party.dto.PartyReqDto;
import com.oneovern.domain.party.dto.PartyResDto;
import com.oneovern.domain.party.entity.Party;
import com.oneovern.domain.party.enums.PartyStatus;
import com.oneovern.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartyService {

    private final PartyRepository partyRepository;
    private final OttPlanRepository ottPlanRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createParty(Long planId, Member member, PartyReqDto dto) {
        OttPlan ottPlan = ottPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 요금제입니다."));

        Party party = Party.builder()
                .partyName(dto.getPartyName())
                .ottAccountId(dto.getOttAccountId())
                .ottAccountPassword(dto.getOttAccountPassword())
                .bank(dto.getBank())
                .bankAccount(dto.getBankAccount())
                .partyStatus(PartyStatus.RECRUITING)
                .ottPlan(ottPlan)
                .leader(member)
                .build();

        Party savedParty = partyRepository.save(party);
        return savedParty.getId();
    }

    public List<PartyResDto.PartyInquiryDto> getPartiesByOtt(Long ottId) {
        return partyRepository.findPartyDetailsByOttId(ottId).stream()
                .map(proj -> PartyResDto.PartyInquiryDto.builder()
                        .partyId(proj.getPartyId())
                        .partyName(proj.getPartyName())
                        .planName(proj.getPlanName())
                        .leaderReliability(proj.getLeaderReliability())
                        .currentMemberCount(proj.getMemberCount())
                        .maxPeople(4)
                        .partyStatus(PartyStatus.valueOf(proj.getPartyStatus().toUpperCase()))
                        .build())
                .collect(Collectors.toList());
    }

    public PartyResDto.PartyDetailDto getPartyDetail(Long partyId) {
        PartyDetailProjection proj = partyRepository.findPartyDetailByIdNative(partyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파티입니다."));

        Party party = partyRepository.findById(partyId).get(); // 상세 일반멤버 리스트 조회를 위한 단건 바인딩

        List<PartyResDto.PartyMemberInfoDto> memberInfos = new java.util.ArrayList<>();

        memberInfos.add(PartyResDto.PartyMemberInfoDto.builder()
                .nickname(party.getLeader().getNickname())
                .reliabilityScore(party.getLeader().getReliabilityScore())
                .isLeader(true)
                .build());

        if (party.getPartyMembers() != null) {
            party.getPartyMembers().forEach(pm -> {
                memberInfos.add(PartyResDto.PartyMemberInfoDto.builder()
                        .nickname(pm.getMember().getNickname())
                        .reliabilityScore(pm.getMember().getReliabilityScore())
                        .isLeader(false)
                        .build());
            });
        }

        return PartyResDto.PartyDetailDto.builder()
                .partyName(proj.getPartyName())
                .planName(proj.getPlanName())
                .maxPeople(party.getOttPlan().getMaxMembers())
                .currentMemberCount(memberInfos.size())
                .monthlyPrice(party.getOttPlan().getMonthlyPrice())
                .partyMembers(memberInfos)
                .build();
    }
}