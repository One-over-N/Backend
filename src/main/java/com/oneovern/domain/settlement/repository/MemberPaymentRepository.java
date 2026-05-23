package com.oneovern.domain.settlement.repository;

import com.oneovern.domain.settlement.entity.MemberPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MemberPaymentRepository extends JpaRepository<MemberPayment,Long> {

    @Query(value="SELECT mp.* FROM member_payment mp "+
            "JOIN party_settlement ps ON mp.party_settlement_id=ps.party_settlement_id "+ //party_settlement 조인(target_date 조회용)
            "JOIN party p ON ps.party_id = p.party_id " + //party 조인(party_name,ott 조회용)
            "JOIN ott_plan op ON p.ott_plan_id = op.ott_plan_id " + //ott_plan 조인(plan_name, ott_plan  조회용)
            "JOIN ott o ON op.ott_id = o.ott_id " + //ott 조인(ott 이름 조회용)
            "WHERE mp.member_id=:memberId "+
            "AND ps.target_date BETWEEN :startDate AND :endDate "+ //이번 달 항목 조회
            "AND (:cursor IS NULL OR mp.member_payment_id<:cursor) "+ //커서보다 작은지 확인
            "ORDER BY mp.member_payment_id DESC "+ //최신순 정렬(id 특성상 클 수록 최신)
            "LIMIT :size",//size만큼 제한
            nativeQuery = true
    )
    List<MemberPayment> findMemberPaymentsByCursor(
            @Param("memberId") Long id,
            @Param("cursor") Long cursor,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("size") int size);
}
