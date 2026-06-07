package com.oneovern.domain.member.entity;

import com.oneovern.domain.notification.entity.Notification;
import com.oneovern.domain.party.entity.mapping.JoinRequest;
import com.oneovern.domain.party.entity.mapping.PartyMember;
import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Min(0)
    @Max(100)
    @Builder.Default
    @Column(name = "reliability_score", nullable = false)
    private int reliabilityScore=50;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) //DB에서 cascade 설정 필요
    private List<ReliabilityHistory> reliabilityHistories=new ArrayList<>(); //reliabilty_history와의 연관관계

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications=new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinRequest> joinRequests=new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyMember> partyMembers=new ArrayList<>();

    //마이페이지 프로필(닉네임, 이메일) 수정을 위한 메서드
    public void updateProfile(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
    public void updateReliabilityScore(int delta) {
        this.reliabilityScore = Math.max(0, Math.min(100, this.reliabilityScore + delta));
    }


}
