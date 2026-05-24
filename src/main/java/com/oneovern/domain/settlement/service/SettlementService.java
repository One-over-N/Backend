package com.oneovern.domain.settlement.service;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.settlement.converter.SettlementConverter;
import com.oneovern.domain.settlement.dto.SettlementResDto;
import com.oneovern.domain.settlement.dto.projection.CurrentMemberPaymentProjection;
import com.oneovern.domain.settlement.repository.MemberPaymentRepository;
import com.oneovern.global.PageResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettlementService {

    private final MemberPaymentRepository memberPaymentRepository;

    //이번 달 정산
    public PageResDto<SettlementResDto.CurrentMemberPaymentInfo> getCurrentMemberPayments(Member member, Long cursor) {

        final int FIXED_SIZE=10;

        //이번 달 계산
        LocalDate today = LocalDate.now();
        LocalDate startDate=today.withDayOfMonth(1);
        LocalDate endDate=today.withDayOfMonth(today.lengthOfMonth());

        //이번 달 정산 조회
        List<CurrentMemberPaymentProjection> memberPayments=memberPaymentRepository.findMemberPaymentsByCursor(
                member.getId(),
                cursor,
                startDate,
                endDate,
                FIXED_SIZE+1
        );

        //커서가 마지막 값인지 확인
        boolean isLast=true;
        if(memberPayments.size()>FIXED_SIZE){
            isLast=false;
            memberPayments.remove(FIXED_SIZE); //마지막 값인지 확인하기 위해 조회한 값 제거
        }


        //nextCursor 계산
        Long nextCursor = null;
        if (!memberPayments.isEmpty() && !isLast) {
            nextCursor = memberPayments.get(memberPayments.size() - 1).getMemberPaymentId(); // 마지막 데이터의 PK를 커서로 지정
        }

        //dDay 계산
        List<Integer> dDayList=memberPayments.stream()
                .map(projection -> {
                    long daysLeft= ChronoUnit.DAYS.between(today, projection.getTargetDate());
                    return(daysLeft>=0&&daysLeft<=3)?(int) daysLeft:null;
                })
                .collect(Collectors.toList());




        //payments->PageResDto
        return SettlementConverter.toCurrentMemberPaymentPage(memberPayments, dDayList , isLast, nextCursor);
    }
}
