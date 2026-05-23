package com.oneovern.domain.settlement.entity;

import com.oneovern.domain.party.entity.Party;
import com.oneovern.domain.settlement.enums.SettlementStatus;
import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "party_settlement")
public class PartySettlement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_settlement_id")
    private Long id;

    @Column(name = "settlement_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SettlementStatus settlementStatus;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @Column(name = "target_amount", nullable = false)
    private int targetAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;
}
