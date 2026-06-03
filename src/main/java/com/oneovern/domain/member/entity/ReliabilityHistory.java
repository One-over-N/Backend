package com.oneovern.domain.member.entity;

import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reliability_history")
public class ReliabilityHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reliability_history_id")
    private Long id;

    @Column(name = "change_score", nullable = false)
    private Integer changeScore;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Builder.Default
    @Column(name = "penalty_applied", nullable = false)
    private boolean penaltyApplied=false;

    @ManyToOne(fetch = FetchType.LAZY) // member와의 연관관계
    @JoinColumn(name="member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;
}
