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

    @Column(name = "settlement_status")
    @Enumerated(EnumType.STRING)
    private SettlementStatuses settlementStatus;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;
}
