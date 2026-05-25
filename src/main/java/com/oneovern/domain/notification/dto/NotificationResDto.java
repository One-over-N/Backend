package com.oneovern.domain.notification.dto;

import com.oneovern.domain.notification.enums.NotificationType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationResDto {

    // 알림 목록 조회
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

    // 알림 읽음 상태 변경
    @Builder
    public record NotificationRead (
            List<Long> notificationIdList,
            LocalDateTime updatedAt
    ){
    }
}
