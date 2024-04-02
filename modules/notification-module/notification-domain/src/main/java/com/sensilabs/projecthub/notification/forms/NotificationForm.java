package com.sensilabs.projecthub.notification.forms;

import com.sensilabs.projecthub.notification.model.NotificationParam;
import com.sensilabs.projecthub.notification.model.NotificationType;

import java.time.Instant;
import java.util.List;



public interface NotificationForm {


    NotificationType getType();
    NotificationChannel getChannel();
    List<NotificationParam> getParams();

    String getReceiver();
    //Priority getPriority();
   // Instant getCreatedOn();



}
