package com.oneovern.domain.party.entity.mapping;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.party.entity.Party;
import com.oneovern.domain.party.enums.RequestStatus;
import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Column(name = "join_request_id")
    private Long id;

    @Builder.Default
    @Column(name = "request_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus= RequestStatus.PENDING;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @ManyToOne(fetch = FetchType.LAZY) // member와의 연관관계
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY) // party와의 연관관계
    @JoinColumn(name = "party_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Party party;

}
