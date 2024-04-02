package com.sensilabs.projecthub.notification;


import com.sensilabs.projecthub.notification.forms.NotificationForm;
import com.sensilabs.projecthub.notification.model.Notification;

public interface NotificationService {

    Notification save(NotificationForm notificationForm, String createdById);

}

