package com.oneovern.domain.settlement.repository;

import com.oneovern.domain.settlement.dto.projection.CurrentMemberPaymentProjection;
import com.oneovern.domain.settlement.dto.projection.MemberPaymentSummaryProjection;
import com.oneovern.domain.settlement.entity.MemberPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MemberPaymentRepository extends JpaRepository<MemberPayment,Long> {

    @Query(value="SELECT mp.member_payment_id AS memberPaymentId, p.party_id AS partyId, op.ott_plan_id AS planId, " +
            "mp.payment_amount AS paymentAmount, mp.payment_status AS paymentStatus, ps.target_date AS targetDate, "+
            "p.party_name AS partyName, o.ott_name AS ottName, op.plan_name AS planName "+
            "FROM member_payment mp "+
            "JOIN party_settlement ps ON mp.party_settlement_id=ps.party_settlement_id "+ //party_settlement 조인(target_date 조회용)
            "JOIN party p ON ps.party_id = p.party_id " + //party 조인(party_name,ott 조회용)
            "JOIN ott_plan op ON p.ott_plan_id = op.ott_plan_id " + //ott_plan 조인(plan_name, ott_plan  조회용)
            "JOIN ott o ON op.ott_id = o.ott_id " + //ott 조인(ott_name 이름 조회용)
            "WHERE mp.member_id=:memberId "+
            "AND ps.target_date BETWEEN :startDate AND :endDate "+ //이번 달 항목 조회
            "AND (:cursor IS NULL OR mp.member_payment_id<:cursor) "+ //커서보다 작은지 확인
            "ORDER BY mp.member_payment_id DESC "+ //최신순 정렬(id 특성상 클 수록 최신)
            "LIMIT :size",//size만큼 제한
            nativeQuery = true
    )
    List<CurrentMemberPaymentProjection> findMemberPaymentsByCursor(
            @Param("memberId") Long id,
            @Param("cursor") Long cursor,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("size") int size);

    @Query(
            value = """ 
            SELECT 
                -- currentMonthBillingAmount: 이번 달 지불해야 하는 총 금액
                COALESCE(SUM(mp.payment_amount), 0) AS currentMonthBillingAmount, 
                            
                -- savedAmount: ~이번 달 말 전체 누적 절약 금액
                (SELECT COALESCE(SUM(sub_ps.target_amount-sub_mp.payment_amount), 0)
                    FROM member_payment sub_mp
                    JOIN party_settlement sub_ps ON sub_mp.party_settlement_id=sub_ps.party_settlement_id
                    WHERE sub_mp.member_id=:memberId
                        AND sub_ps.target_date<=:endDate) AS savedAmount, 
                
                -- completedPaymentCount: 이번 달 실 결제 완료 수
                COUNT(CASE WHEN mp.payment_status='PAID' THEN 1 END) AS completedPaymentCount, 
                
                -- totalPaymentCount: 이번 달 총 결제(완료+미완료) 수
                COUNT(mp.member_payment_id) AS totalPaymentCount
                        
            FROM member_payment mp
            JOIN party_settlement ps ON mp.party_settlement_id=ps.party_settlement_id
            WHERE mp.member_id=:memberId
                AND ps.target_date BETWEEN :startDate AND :endDate
            """,
            nativeQuery = true
    )
    MemberPaymentSummaryProjection findMemberPaymentSummaryByMemberAndDate(
            @Param("memberId") Long id,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


}
