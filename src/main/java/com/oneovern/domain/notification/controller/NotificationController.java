package com.oneovern.domain.notification.controller;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.notification.dto.NotificationResDto;
import com.oneovern.domain.notification.exception.code.NotificationSuccessCode;
import com.oneovern.domain.notification.service.NotificationService;
import com.oneovern.global.ApiResponse;
import com.oneovern.global.PageResDto;
import com.oneovern.global.apiPayload.code.BaseSuccessCode;
import com.oneovern.global.security.annotation.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    //알림 목록 조회
    @GetMapping("")
    public ApiResponse<PageResDto<NotificationResDto.NotificationInfo>> getNotificationList(
            @AuthUser Member member,
            @RequestParam(name = "cursor", required = false) Long cursor
            )
    {
        BaseSuccessCode code= NotificationSuccessCode.GET_NOTIFICATION_LIST;
        return ApiResponse.onSuccess(code, notificationService.getNotificationList(member, cursor));
    }

    //알림 읽음 상태 변경
    @PatchMapping("")
    public ApiResponse<NotificationResDto.NotificationRead> readNotifications(
            @AuthUser Member member,
            @RequestBody List<String> notificationIdList
    )
    {
        BaseSuccessCode code= NotificationSuccessCode.READ_NOTIFICATION;
        return ApiResponse.onSuccess(code, notificationService.readNotifications(member, notificationIdList));
    }

    //읽지 않은 알림 개수 조회
    @GetMapping("/unread-count")
    public ApiResponse<Long> getUnreadNotificationCount(
            @AuthUser Member member
    )
    {
        BaseSuccessCode code= NotificationSuccessCode.GET_UNREAD_NOTIFICATION_COUNT;
        return ApiResponse.onSuccess(code, notificationService.getUnreadNotificationCount(member));
    }
}
