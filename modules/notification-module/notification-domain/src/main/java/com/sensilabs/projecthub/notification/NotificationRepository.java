package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.model.Notification;

public interface NotificationRepository {
    Notification save(Notification notification);
}
