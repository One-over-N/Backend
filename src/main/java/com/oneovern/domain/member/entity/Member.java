package com.oneovern.domain.member.entity;

import com.oneovern.domain.notification.entity.Notification;
import com.oneovern.domain.party.entity.mapping.JoinRequest;
import com.oneovern.domain.party.entity.mapping.PartyMember;
import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Builder.Default
    @Column(name = "reliability_score", nullable = false)
    private Integer reliabilityScore=50; //반정규화

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) //DB에서 cascade 설정 필요
    private List<ReliabilityHistory> reliabilityHistories=new ArrayList<>(); //reliabilty_history와의 연관관계

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications=new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinRequest> joinRequests=new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyMember> partyMembers=new ArrayList<>();

}
