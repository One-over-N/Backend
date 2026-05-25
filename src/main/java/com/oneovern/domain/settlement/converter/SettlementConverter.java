package com.oneovern.domain.settlement.converter;

import com.oneovern.domain.settlement.dto.SettlementResDto;
import com.oneovern.domain.settlement.dto.projection.CurrentMemberPaymentProjection;
import com.oneovern.domain.settlement.dto.projection.MemberPaymentSummaryProjection;
import com.oneovern.global.PageResDto;

import java.util.ArrayList;
import java.util.List;

public class SettlementConverter {

    //MemberPayment ->paymentInfo
    public static SettlementResDto.CurrentMemberPaymentInfo toPaymentInfo(CurrentMemberPaymentProjection projection, Integer dDay)
    {
        return SettlementResDto.CurrentMemberPaymentInfo.builder()
                .memberPaymentId(projection.getMemberPaymentId())
                .partyName(projection.getPartyName())
                .ottName(projection.getOttName())
                .planName(projection.getPlanName())
                .paymentStatus(projection.getPaymentStatus())
                .paymentAmount(projection.getPaymentAmount())
                .targetDate(projection.getTargetDate())
                .dDay(dDay)
                .build();
    }

    //MemberPayment 리스트->pageResDto
    public static PageResDto<SettlementResDto.CurrentMemberPaymentInfo> toCurrentMemberPaymentPage(
            List<CurrentMemberPaymentProjection> payments,
            List<Integer> dDayList,
            boolean isLast,
            Long nextCursor
    ){
        List<SettlementResDto.CurrentMemberPaymentInfo> infoList = new ArrayList<>();

        //dDay+memberPayment
        for(int i=0; i<payments.size(); i++){
            Integer dDay=(i<dDayList.size())?dDayList.get(i):null;

            infoList.add(toPaymentInfo(payments.get(i), dDay));
        }

        //pageResDto로 변환
        return PageResDto.<SettlementResDto.CurrentMemberPaymentInfo>builder()
                        .dataList(infoList)
                        .nextCursor(nextCursor)
                        .isLast(isLast)
                        .build();
    }


    public static SettlementResDto.MemberPaymentSummary toMemberPaymentSummary(MemberPaymentSummaryProjection projection) {
        return SettlementResDto.MemberPaymentSummary.builder()
                .currentMonthBillingAmount(projection.getCurrentMonthBillingAmount())
                .savedAmount(projection.getSavedAmount())
                .completedPaymentCount(projection.getCompletedPaymentCount())
                .totalPaymentCount(projection.getTotalPaymentCount())
                .build();
    }
}
