package com.oneovern.domain.member.entity;

import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reliability_history")
public class ReliabilityHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "change_score", nullable = false)
    private Integer changeScore;

    @Column(name = "after_score", nullable = false)
    private Integer afterScore;

    @Column(name = "reason", nullable = false)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY) // member와의 연관관계
    @JoinColumn(name="member_id", nullable = false)
    private Member member;
}
