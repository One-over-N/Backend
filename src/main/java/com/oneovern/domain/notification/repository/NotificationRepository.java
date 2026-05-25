package com.oneovern.domain.notification.repository;

import com.oneovern.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(
            value = """
            SELECT n.*
            FROM notification n
            WHERE member_id=:memberId
                AND (:cursor IS NULL OR n.notification_id<:cursor) -- 커서 없으면 첫페이지, 있으면 id 작은 notification(cursor보다 오래된 것)
            ORDER BY n.notification_id DESC -- 생성일자 내림차순 정렬
            LIMIT :size
            """,
            nativeQuery=true
    )
    List<Notification> findByIdAndCursor(
            @Param("memberId") Long id,
            @Param("cursor") Long cursor,
            @Param("size") int size);
}
