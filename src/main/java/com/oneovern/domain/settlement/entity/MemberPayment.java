package com.oneovern.domain.settlement.entity;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.settlement.enums.PaymentStatus;
import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_payment")
public class MemberPayment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_payment_id")
    private Long id;

    @Column(name = "payment_amount", nullable = false)
    private Integer paymentAmount;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Builder.Default
    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus= PaymentStatus.UNPAID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_settlement_id", nullable = false)
    private PartySettlement partySettlement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void updateStatus(PaymentStatus paymentStatus, LocalDateTime now) {
        this.paymentStatus=paymentStatus;
        this.paidAt = now;
    }
}
