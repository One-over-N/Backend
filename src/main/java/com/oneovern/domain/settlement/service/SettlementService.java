package com.oneovern.domain.settlement.service;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.entity.ReliabilityHistory;
import com.oneovern.domain.member.repository.MemberRepository;
import com.oneovern.domain.member.repository.ReliabilityHistoryRepository;
import com.oneovern.domain.settlement.converter.SettlementConverter;
import com.oneovern.domain.settlement.dto.SettlementResDto;
import com.oneovern.domain.settlement.dto.projection.CurrentMemberPaymentProjection;
import com.oneovern.domain.settlement.dto.projection.MemberPaymentHistoryProjection;
import com.oneovern.domain.settlement.dto.projection.MemberPaymentSummaryProjection;
import com.oneovern.domain.settlement.entity.MemberPayment;
import com.oneovern.domain.settlement.enums.PaymentStatus;
import com.oneovern.domain.settlement.exception.SettlementException;
import com.oneovern.domain.settlement.exception.code.SettlementErrorCode;
import com.oneovern.domain.settlement.repository.MemberPaymentRepository;
import com.oneovern.global.PageResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettlementService {

    private final MemberPaymentRepository memberPaymentRepository;
    private final MemberRepository memberRepository;
    private final ReliabilityHistoryRepository reliabilityHistoryRepository;
    private final Clock clock;

    @Value("${app.paging.default-size}")
    private int defaultPageSize;

    public PageResDto<SettlementResDto.CurrentMemberPaymentInfo> getCurrentMemberPayments(Member member, Long cursor) {

        LocalDate today = LocalDate.now(clock);
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());

        List<CurrentMemberPaymentProjection> memberPayments = memberPaymentRepository.findMemberPaymentsByCursor(
                member.getId(), cursor, startDate, endDate, defaultPageSize + 1);

        boolean isLast = true;
        List<CurrentMemberPaymentProjection> modifiablePayments = new ArrayList<>(memberPayments);
        if (modifiablePayments.size() > defaultPageSize) {
            isLast = false;
            modifiablePayments.remove(defaultPageSize);
        }

        Long nextCursor = null;
        if (!modifiablePayments.isEmpty() && !isLast) {
            nextCursor = modifiablePayments.get(modifiablePayments.size() - 1).getMemberPaymentId();
        }

        List<Integer> dDayList = memberPayments.stream()
                .map(projection -> {
                    long daysLeft = ChronoUnit.DAYS.between(today, projection.getTargetDate());
                    return (daysLeft >= 0 && daysLeft <= 3) ? (int) daysLeft : null;
                })
                .collect(Collectors.toList());

        return SettlementConverter.toCurrentMemberPaymentPage(memberPayments, dDayList, isLast, nextCursor);
    }

    public SettlementResDto.MemberPaymentSummary getMemberPaymentSummary(Member member) {

        LocalDate today = LocalDate.now(clock);
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());

        MemberPaymentSummaryProjection projection = memberPaymentRepository.findMemberPaymentSummaryByMemberAndDate(
                member.getId(), startDate, endDate);

        return SettlementConverter.toMemberPaymentSummary(projection);
    }

    public PageResDto<SettlementResDto.MemberPaymentHistory> getMemberPaymentHistory(Member member, Long cursor) {

        List<MemberPaymentHistoryProjection> memberPaymentHistory = memberPaymentRepository.findMemberPaymentHistoryByCursor(
                member.getId(), cursor, defaultPageSize + 1);

        boolean isLast = true;
        List<MemberPaymentHistoryProjection> modifiablePaymentHistory = new ArrayList<>(memberPaymentHistory);
        if (modifiablePaymentHistory.size() > defaultPageSize) {
            isLast = false;
            modifiablePaymentHistory.remove(defaultPageSize);
        }

        Long nextCursor = null;
        if (!modifiablePaymentHistory.isEmpty() && !isLast) {
            nextCursor = modifiablePaymentHistory.get(modifiablePaymentHistory.size() - 1).getMemberPaymentId();
        }

        return SettlementConverter.toMemberPaymentHistoryPage(modifiablePaymentHistory, isLast, nextCursor);
    }

    @Transactional
    public SettlementResDto.PaymentStatusUpdate updateMemberPaymentStatus(
            Member member,
            Long memberPaymentId,
            PaymentStatus paymentStatus) {

        MemberPayment memberPayment = memberPaymentRepository
                .findById(memberPaymentId)
                .orElseThrow(() -> new SettlementException(SettlementErrorCode.MEMBER_PAYMENT_NOT_FOUND));

        if (!memberPayment.getMember().getId().equals(member.getId())) {
            throw new SettlementException(SettlementErrorCode.MEMBER_PAYMENT_ACCESS_DENIED);
        }

        memberPayment.updateStatus(paymentStatus, LocalDateTime.now(clock));

        return SettlementConverter.toPaymentStatusUpdate(memberPayment);
    }
}