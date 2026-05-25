package com.oneovern.domain.notification.repository;

import com.oneovern.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(
            // cursor보다 id가 낮은 값(오래 된 값)만 10개(고정 size) 조회하여 페이징
            value = """
            SELECT n.*
            FROM notification n
            WHERE member_id=:memberId
                AND (:cursor IS NULL OR n.notification_id<:cursor) 
            ORDER BY n.notification_id DESC
            LIMIT :size
            """,
            nativeQuery=true
    )
    List<Notification> findByMemberIdAndCursor(
            @Param("memberId") Long id,
            @Param("cursor") Long cursor,
            @Param("size") int size);
}
