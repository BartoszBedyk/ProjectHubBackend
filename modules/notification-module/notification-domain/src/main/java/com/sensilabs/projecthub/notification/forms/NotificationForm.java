package com.sensilabs.projecthub.notification.forms;

import com.sensilabs.projecthub.notification.model.NotificationType;

import java.util.Map;


public interface NotificationForm {


    NotificationType getType();

    NotificationChannel getChannel();

    Map<String, String> getParams();

    String getReceiver();

    String getLink();


}
