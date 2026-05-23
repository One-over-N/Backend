package com.oneovern.domain.party.entity;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.ott.entity.OttPlan;
import com.oneovern.domain.party.entity.mapping.JoinRequest;
import com.oneovern.domain.party.entity.mapping.PartyMember;
import com.oneovern.domain.party.enums.PartyStatuses;
import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "party")
public class Party extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "party_name")
    private String partyName;

    @Column(name = "ott_account_id")
    private String ottAccountId;

    @Column(name = "ott_account_password")
    private String ottAccountPassword;

    @Column(name = "bank")
    private String bankAccount;

    @Column(name = "party_status")
    @Enumerated(EnumType.STRING)
    private PartyStatuses partyStatus;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @ManyToOne(fetch=FetchType.LAZY) // ott plan과의 연관관계
    @JoinColumn(name = "ott_plan_id")
    private OttPlan ottPlan;

    @ManyToOne(fetch = FetchType.LAZY) // member와의 연관관계
    @JoinColumn(name = "leader_id")
    private Member leader;

    @OneToMany(mappedBy = "party")
    private List<JoinRequest> joinRequests=new ArrayList<>();

    @OneToMany(mappedBy = "party")
    private List<PartyMember> partyMembers=new ArrayList<>();
}
