package com.oneovern.domain.settlement.converter;

import com.oneovern.domain.settlement.dto.SettlementResDto;
import com.oneovern.domain.settlement.entity.MemberPayment;
import com.oneovern.global.PageResDto;

import java.util.ArrayList;
import java.util.List;

public class SettlementConverter {

    //MemberPayment->paymentInfo
    public static SettlementResDto.CurrentMemberPaymentInfo toPaymentInfo(MemberPayment memberPayment, Integer dDay)
    {
        return SettlementResDto.CurrentMemberPaymentInfo.builder()
                .partyId(memberPayment.getPartySettlement().getParty().getId())
                .planId(memberPayment.getPartySettlement().getParty().getOttPlan().getId())
                .partyName(memberPayment.getPartySettlement().getParty().getPartyName())
                .ottName(memberPayment.getPartySettlement().getParty().getOttPlan().getOtt().getOttName())
                .planName(memberPayment.getPartySettlement().getParty().getOttPlan().getPlanName())
                .paymentStatus(memberPayment.getPaymentStatus())
                .paymentAmount(memberPayment.getPaymentAmount())
                .targetDate(memberPayment.getPartySettlement().getTargetDate())
                .dDay(dDay)
                .build();
    }

    //MemberPayment 리스트->pageResDto
    public static PageResDto toCurrentMemberPaymentPage(
            List<MemberPayment> payments,
            List<Integer> dDayList,
            boolean isLast,
            Long nextCursor
    ){
        List<SettlementResDto.CurrentMemberPaymentInfo> infoList = new ArrayList<>();

        //dDay+memberPayment
        for(int i=0; i<payments.size(); i++){
            infoList.add(toPaymentInfo(payments.get(i), dDayList.get(i)));
        }

        //pageResDto로 변환
        return PageResDto.<SettlementResDto.CurrentMemberPaymentInfo>builder()
                        .dataList(infoList)
                        .nextCursor(nextCursor)
                        .isLast(isLast)
                        .build();
    }


}
