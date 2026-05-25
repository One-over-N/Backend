package com.oneovern.domain.notification.converter;

import com.oneovern.domain.notification.dto.NotificationResDto;
import com.oneovern.domain.notification.entity.Notification;
import com.oneovern.global.PageResDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationConverter {

    //notification->notificationResDto.NotificationInfo
    public static NotificationResDto.NotificationInfo toNotificationInfo(Notification notification){
        return NotificationResDto.NotificationInfo.builder()
                .notificationId(notification.getId())
                .title(notification.getNotificationType().getDescription())
                .message(notification.getContent())
                .notificationType(notification.getNotificationType())
                .targetUrl(notification.getTargetUrl())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    //notificationList->PageResDto
    public static PageResDto<NotificationResDto.NotificationInfo> toNotificationListPage(
            List<Notification> notificationList,
            boolean isLast,
            Long nextCursor
    ){
        List<NotificationResDto.NotificationInfo> notificationInfoList = notificationList.stream()
                .map(NotificationConverter::toNotificationInfo)
                .toList();

        return PageResDto.<NotificationResDto.NotificationInfo>builder()
                .dataList(notificationInfoList)
                .isLast(isLast)
                .nextCursor(nextCursor)
                .build();
    }

    public static NotificationResDto.NotificationRead toNotificationRead(List<Notification> targetNotifications, Clock clock) {

        //notification List->id List
        List<Long> readIds=targetNotifications.stream()
                .map(Notification::getId)
                .toList();

        return NotificationResDto.NotificationRead.builder()
                .notificationIdList(readIds)
                .updatedAt(LocalDateTime.now(clock))
                .build();
    }
}
