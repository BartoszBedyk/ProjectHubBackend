package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.forms.NotificationForm;
import com.sensilabs.projecthub.notification.model.Notification;

import java.util.HashMap;
import java.util.Map;

public class NotificationRepositoryMock implements NotificationRepository {

    Map<String, Notification> mockDB = new HashMap<>();




    @Override
    public Notification save(Notification notification) {
        mockDB.put(notification.getId(), notification);
        return notification;
    }
}
