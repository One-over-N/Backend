package com.oneovern.domain.party.entity.mapping;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PartyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyMemberId;

    @Column(nullable = false)
    private Long partyId;

    @Column(nullable = false)
    private Long memberId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}