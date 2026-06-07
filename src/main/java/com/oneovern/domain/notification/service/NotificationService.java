package com.oneovern.domain.notification.service;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.notification.converter.NotificationConverter;
import com.oneovern.domain.notification.dto.NotificationResDto;
import com.oneovern.domain.notification.entity.Notification;
import com.oneovern.domain.notification.exception.NotificationException;
import com.oneovern.domain.notification.exception.code.NotificationErrorCode;
import com.oneovern.domain.notification.repository.NotificationRepository;
import com.oneovern.global.PageResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final Clock clock;

    @Value("${app.paging.default-size}")
    private int defaultPageSize;


    //알림 목록 조회
    public PageResDto<NotificationResDto.NotificationInfo> getNotificationList(Member member, Long cursor) {

        //알림 목록 조회
        List<Notification> notificationList= new ArrayList<>(
                notificationRepository.findByMemberIdAndCursor(member.getId(), cursor, defaultPageSize+1));

        //마지막인지 확인
        boolean isLast=true;
        if (notificationList.size()>defaultPageSize) {
            isLast=false;
            notificationList.remove(defaultPageSize);
        }

        //다음 커서 값
        Long nextCursor=null;
        if (!notificationList.isEmpty()&&!isLast) {
            nextCursor=notificationList.getLast().getId();
        }

        //notificationList->PageResDto
        return NotificationConverter.toNotificationListPage(notificationList, isLast, nextCursor);
    }

    // 알림 읽음 상태 변경
    @Transactional
    public NotificationResDto.NotificationRead readNotifications(
            Member member,
            List<String> notificationIdList) {

        //notificationIdList 값 확인
        if(notificationIdList==null||notificationIdList.isEmpty()){
            throw new NotificationException(NotificationErrorCode.NO_NOTIFICATION_TO_READ);
        }

        List<Notification> targetNotifications;
        //전체 읽음 처리
        if(notificationIdList.contains("ALL")){
            targetNotifications = notificationRepository.findUnreadNotificationByMemberId(member.getId());

            if (!targetNotifications.isEmpty()) {
                notificationRepository.UpdateUnreadNotificationByMemberId(member.getId());
            }
        }
        else{ //리스트의 알림만 읽음 처리
            List<Long> ids=notificationIdList.stream()
                    .map(Long::parseLong)
                    .toList();

            targetNotifications=notificationRepository.findAllById(ids);

            //member의 notification인지 검증
            targetNotifications.stream()
                    .filter(n->!n.getMember().getId().equals(member.getId()))
                    .findAny()
                    .ifPresent(n->{throw new NotificationException(NotificationErrorCode.NOTIFICATION_NOT_OWNED);});
        }

        //isRead 상태 변경
        targetNotifications.forEach(Notification::readNotification);

        //->notificationResDto.notificationStatusUpdate
        return NotificationConverter.toNotificationRead(targetNotifications, clock);
    }

    public Long getUnreadNotificationCount(Member member) {
        Long unreadNotificationCount=notificationRepository.countUnreadNotificationByMemberId(member.getId());

        return unreadNotificationCount;
    }
}
