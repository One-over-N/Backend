package com.oneovern.domain.member.repository;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.entity.ReliabilityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReliabilityHistoryRepository extends JpaRepository<ReliabilityHistory, Long> {

    //최신순으로 상위 3건 가져옴
    @Query(value = "SELECT * FROM reliability_history rh " +
            "WHERE rh.member_id = :memberId " +
            "ORDER BY rh.created_at DESC LIMIT 3",
            nativeQuery = true)
    List<ReliabilityHistory> findTop3ByMemberIdOrderByCreatedAtDesc(@Param("memberId") Long memberId);

    default List<ReliabilityHistory> findTop3ByMemberOrderByCreatedAtDesc(Member member) {
        return findTop3ByMemberIdOrderByCreatedAtDesc(member.getId());
    }
}