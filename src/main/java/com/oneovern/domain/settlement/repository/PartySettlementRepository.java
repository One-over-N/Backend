package com.oneovern.domain.settlement.repository;

import com.oneovern.domain.settlement.entity.PartySettlement;
import com.oneovern.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface PartySettlementRepository extends JpaRepository<PartySettlement, Long> {
    Optional<PartySettlement> findByPartyAndTargetDate(Party party, LocalDate targetDate);
}