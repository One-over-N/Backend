package com.oneovern.domain.settlement.repository;

import com.oneovern.domain.settlement.entity.PartySettlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartySettlementRepository extends JpaRepository<PartySettlement,Long> {
}
