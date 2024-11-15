package com.app.faksfit.repository;

import com.app.faksfit.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findByNotificationTitle(String title);

    Notification findByNotificationId(Long notificationId);

}
