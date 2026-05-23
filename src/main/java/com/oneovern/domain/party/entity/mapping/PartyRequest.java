package com.oneovern.domain.party.entity.mapping;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PartyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyRequestId;

    @Column(nullable = false)
    private Long partyId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime processedAt;
}