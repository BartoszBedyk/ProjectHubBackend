package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.model.Notification;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NotificationRepositoryMock implements NotificationRepository {

    Map<String, Notification> mockDB = new HashMap<>();




    @Override
    public Notification save(Notification notification) {
        mockDB.put(notification.getId(), notification);
        return notification;
    }

    @Override
    public Optional<Notification> findById(String id) {
        return Optional.empty();
    }


}
