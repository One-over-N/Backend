package com.oneovern.domain.notification.service;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.notification.converter.NotificationConverter;
import com.oneovern.domain.notification.dto.NotificationResDto;
import com.oneovern.domain.notification.entity.Notification;
import com.oneovern.domain.notification.repository.NotificationRepository;
import com.oneovern.global.PageResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

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
}
