package com.oneovern.domain.settlement.entity;

import com.oneovern.domain.party.entity.Party;
import com.oneovern.domain.settlement.enums.SettlementStatuses;
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
    private Long id;

    @Column(name = "settlement_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SettlementStatuses settlementStatus;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @Column(name = "target_amount", nullable = false)
    private Integer targetAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;
}
