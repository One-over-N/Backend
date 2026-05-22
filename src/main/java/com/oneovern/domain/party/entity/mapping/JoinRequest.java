package com.oneovern.domain.party.entity.mapping;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.party.entity.Party;
import com.oneovern.domain.party.enums.RequestStatuses;
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
@Table(name = "join_request")
public class JoinRequest  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    private RequestStatuses requestStatus;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @ManyToOne(fetch = FetchType.LAZY) // member와의 연관관계
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY) // party와의 연관관계
    @JoinColumn(name = "party_id")
    private Party party;

}
