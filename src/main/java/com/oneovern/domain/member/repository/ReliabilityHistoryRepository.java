package com.oneovern.domain.member.repository;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.entity.ReliabilityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReliabilityHistoryRepository extends JpaRepository<ReliabilityHistory, Long> {

    //최신순으로 상위 3건 가져옴
    List<ReliabilityHistory> findTop3ByMemberOrderByCreatedAtDesc(Member member);
}