package com.oneovern.domain.notification.entity;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.notification.enums.NotificationTypes;
import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="notification")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationTypes notificationType;

    @Column(name="message", nullable = false)
    private String message;

    @Builder.Default
    @Column(name = "is_read", nullable = false)
    private Boolean isRead=false;

    @Column(name="target_url", nullable = false)
    private String targetUrl;

    @ManyToOne(fetch = FetchType.LAZY) // member와의 연관관계
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
