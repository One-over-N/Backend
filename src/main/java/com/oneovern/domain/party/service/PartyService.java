package com.oneovern.domain.party.service;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.repository.MemberRepository;
import com.oneovern.domain.ott.entity.OttPlan;
import com.oneovern.domain.ott.repository.OttPlanRepository;
import com.oneovern.domain.party.dto.PartyReqDto;
import com.oneovern.domain.party.dto.PartyResDto;
import com.oneovern.domain.party.entity.Party;
import com.oneovern.domain.party.enums.PartyStatus;
import com.oneovern.domain.party.enums.RequestStatus;
import com.oneovern.domain.party.exception.PartyErrorCode;
import com.oneovern.domain.party.exception.PartyException;
import com.oneovern.domain.party.repository.PartyRepository;
import com.oneovern.domain.notification.entity.Notification;
import com.oneovern.domain.notification.enums.NotificationType;
import com.oneovern.domain.notification.repository.NotificationRepository;
import com.oneovern.domain.settlement.entity.MemberPayment;
import com.oneovern.domain.settlement.entity.PartySettlement;
import com.oneovern.domain.settlement.enums.SettlementStatus;
import com.oneovern.domain.settlement.repository.MemberPaymentRepository;
import com.oneovern.domain.settlement.repository.PartySettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartyService {

    private final PartyRepository partyRepository;
    private final OttPlanRepository ottPlanRepository;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final PartySettlementRepository partySettlementRepository;
    private final MemberPaymentRepository memberPaymentRepository;

    @Transactional
    public Long createParty(Long planId, Member member, PartyReqDto dto) {
        OttPlan ottPlan = ottPlanRepository.findById(planId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.OTT_PLAN_NOT_FOUND));
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
        return partyRepository.save(party).getId();
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
                        .monthlyPrice(proj.getMonthlyPrice())
                        .partyStatus(PartyStatus.valueOf(proj.getPartyStatus().toUpperCase()))
                        .build())
                .collect(Collectors.toList());
    }

    public PartyResDto.PartyDetailDto getPartyDetail(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.PARTY_NOT_FOUND));

        List<PartyResDto.PartyMemberInfoDto> memberInfos = new java.util.ArrayList<>();
        memberInfos.add(PartyResDto.PartyMemberInfoDto.builder()
                .nickname(party.getLeader().getNickname())
                .reliabilityScore(party.getLeader().getReliabilityScore())
                .isLeader(true)
                .build());

        if (party.getPartyMembers() != null) {
            party.getPartyMembers().forEach(pm ->
                    memberInfos.add(PartyResDto.PartyMemberInfoDto.builder()
                            .nickname(pm.getMember().getNickname())
                            .reliabilityScore(pm.getMember().getReliabilityScore())
                            .isLeader(false)
                            .build())
            );
        }

        return PartyResDto.PartyDetailDto.builder()
                .partyName(party.getPartyName())
                .planName(party.getOttPlan().getPlanName())
                .maxPeople(party.getOttPlan().getMaxMembers())
                .currentMemberCount(memberInfos.size())
                .monthlyPrice(party.getOttPlan().getMonthlyPrice())
                .partyMembers(memberInfos)
                .build();
    }

    @Transactional
    public Long requestJoin(Long partyId, Member member) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.PARTY_NOT_FOUND));

        if (partyRepository.existsActiveJoinRequest(partyId, member.getId()) == 1) {
            throw new PartyException(PartyErrorCode.DUPLICATE_JOIN_REQUEST);
        }

        int currentCount = partyRepository.getCurrentMemberCountNative(partyId);
        if (currentCount >= party.getOttPlan().getMaxMembers()) {
            throw new PartyException(PartyErrorCode.PARTY_FULL);
        }

        partyRepository.saveJoinRequestNative("PENDING", member.getId(), partyId);
        Long requestId = partyRepository.findLatestRequestId(member.getId(), partyId);

        notificationRepository.save(Notification.builder()
                .notificationType(NotificationType.JOIN_REQUEST)
                .content("사용자 '" + member.getNickname() + "'님이 '" + party.getPartyName() + "' 파티 가입을 신청했습니다.")
                .isRead(false)
                .targetUrl("/join-requests/" + requestId)
                .member(party.getLeader())
                .build());

        return partyId;
    }

    @Transactional
    public void processJoinRequest(Long requestId, RequestStatus status, Member leader) {
        Long realLeaderId = partyRepository.findLeaderIdByRequestIdNative(requestId);
        if (realLeaderId == null || !realLeaderId.equals(leader.getId())) {
            throw new PartyException(PartyErrorCode.NOT_PARTY_LEADER);
        }

        Long applicantId = partyRepository.findMemberIdByRequestIdNative(requestId);
        Member applicant = memberRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청자입니다."));

        Long partyId = partyRepository.findPartyIdByRequestIdNative(requestId);
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.PARTY_NOT_FOUND));

        boolean isApproved = (RequestStatus.APPROVED == status);

        if (isApproved) {
            int currentCount = partyRepository.getCurrentMemberCountNative(partyId);
            if (currentCount >= party.getOttPlan().getMaxMembers()) {
                throw new PartyException(PartyErrorCode.PARTY_FULL);
            }

            partyRepository.savePartyMemberNative(partyId, applicantId);

            // 파티 다 찼으면 ACTIVE + 전체 정산 생성
            int newCount = partyRepository.getCurrentMemberCountNative(partyId);
            if (newCount >= party.getOttPlan().getMaxMembers()) {
                partyRepository.updatePartyStatusNative(partyId, "ACTIVE");

                LocalDate targetDate = LocalDate.now().withDayOfMonth(1);
                int perUserAmount = party.getOttPlan().getMonthlyPrice() / party.getOttPlan().getMaxMembers();

                PartySettlement settlement = partySettlementRepository.save(
                        PartySettlement.builder()
                                .party(party)
                                .targetDate(targetDate)
                                .targetAmount(party.getOttPlan().getMonthlyPrice())
                                .settlementStatus(SettlementStatus.PENDING)
                                .build()
                );

                // 리더 정산
                memberPaymentRepository.save(MemberPayment.builder()
                        .member(party.getLeader())
                        .partySettlement(settlement)
                        .paymentAmount(perUserAmount)
                        .build());

                // 기존 파티원 정산 (party_member 테이블에서 조회)
                party.getPartyMembers().forEach(pm ->
                        memberPaymentRepository.save(MemberPayment.builder()
                                .member(pm.getMember())
                                .partySettlement(settlement)
                                .paymentAmount(perUserAmount)
                                .build())
                );

                // 신청자 정산
                memberPaymentRepository.save(MemberPayment.builder()
                        .member(applicant)
                        .partySettlement(settlement)
                        .paymentAmount(perUserAmount)
                        .build());
            }
        }

        partyRepository.updateJoinRequestStatusNative(requestId, status.name());

        notificationRepository.save(Notification.builder()
                .notificationType(isApproved ? NotificationType.JOIN_APPROVED : NotificationType.JOIN_REJECTED)
                .content(isApproved ? "파티 가입이 승인되었습니다." : "파티 가입이 거절되었습니다.")
                .isRead(false)
                .targetUrl("/api/notifications")
                .member(applicant)
                .build());
    }

    public List<PartyResDto.PartyInquiryDto> getMyParties(Member member) {
        return partyRepository.findPartyDetailsByLeaderId(member.getId()).stream()
                .map(proj -> PartyResDto.PartyInquiryDto.builder()
                        .partyId(proj.getPartyId())
                        .partyName(proj.getPartyName())
                        .planName(proj.getPlanName())
                        .leaderReliability(proj.getLeaderReliability())
                        .currentMemberCount(proj.getMemberCount())
                        .maxPeople(4)
                        .monthlyPrice(proj.getMonthlyPrice())
                        .partyStatus(PartyStatus.valueOf(proj.getPartyStatus().toUpperCase()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<PartyResDto.PartyInquiryDto> getJoinedParties(Member member) {
        return partyRepository.findPartyDetailsByMemberId(member.getId()).stream()
                .map(proj -> PartyResDto.PartyInquiryDto.builder()
                        .partyId(proj.getPartyId())
                        .partyName(proj.getPartyName())
                        .planName(proj.getPlanName())
                        .leaderReliability(proj.getLeaderReliability())
                        .currentMemberCount(proj.getMemberCount())
                        .maxPeople(4)
                        .monthlyPrice(proj.getMonthlyPrice())
                        .partyStatus(PartyStatus.valueOf(proj.getPartyStatus().toUpperCase()))
                        .build())
                .collect(Collectors.toList());
    }
}