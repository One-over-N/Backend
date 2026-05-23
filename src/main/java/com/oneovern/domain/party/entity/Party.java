package com.oneovern.domain.party.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Column(nullable = false)
    private String partyName;

    @Column(nullable = false)
    private String ottAccountId;

    @Column(nullable = false)
    private String ottAccountPassword;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String bankAccount;

    @Column(nullable = false)
    private String partyStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startedAt;

    private Long ottPlanId;
    private Long leaderId;
}