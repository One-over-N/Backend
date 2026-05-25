package com.oneovern.domain.notification.dto;

import com.oneovern.domain.notification.enums.NotificationType;
import lombok.Builder;

import java.time.LocalDateTime;

public class NotificationResDto {
    @Builder
    public record NotificationInfo (
            Long notificationId,
            String title,
            String message,
            NotificationType notificationType,
            String targetUrl,
            Boolean isRead,
            LocalDateTime createdAt
    )
    {}
}
