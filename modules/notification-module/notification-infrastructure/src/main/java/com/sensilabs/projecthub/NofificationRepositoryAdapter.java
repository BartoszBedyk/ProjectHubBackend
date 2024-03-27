package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.NotificationRepository;
import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.stereotype.Component;

@Component

public class NofificationRepositoryAdapter implements NotificationRepository {

    private final NotificationRepositoryJpa notificationRepositoryJpa;

    public NofificationRepositoryAdapter(NotificationRepositoryJpa notificationRepositoryJpa) {
        this.notificationRepositoryJpa = notificationRepositoryJpa;
    }

    @Override
    public Notification save(Notification notification) {

        NotificationEntity notificationEntity = NotificationMapper.toNotificationEntity(notification);
        return NotificationMapper.toNotification(notificationRepositoryJpa.save(notificationEntity));

    }
}
