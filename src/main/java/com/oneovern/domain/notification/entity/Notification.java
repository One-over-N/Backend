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

    @Column(name="notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationTypes notificationType;

    @Column(name="message")
    private String message;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name="target_url")
    private String targetUrl;

    @ManyToOne(fetch = FetchType.LAZY) // member와의 연관관계
    @JoinColumn(name = "member_id")
    private Member member;
}
