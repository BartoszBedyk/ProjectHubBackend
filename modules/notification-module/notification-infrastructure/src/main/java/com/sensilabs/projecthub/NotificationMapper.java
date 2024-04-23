package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.model.Notification;
import com.sensilabs.projecthub.notification.model.NotificationParam;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {
    public static NotificationEntity toNotificationEntity(Notification notification){

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .id(notification.getId())
                .channel(notification.getChannel())
                .type(notification.getType())
                .createdById(notification.getCreatedById())
                .createdOn(notification.getCreatedOn().toString())
                .receiver(notification.getReceiver())
                .send(false)
                .build();
        return notificationEntity;
    }

    public static NotificationParamEntity toNotificationParam(NotificationParam notificationParam, NotificationEntity notificationEntity){
        return NotificationParamEntity.builder()
                .id(notificationParam.getId())
                .notification(notificationEntity)
                .name(notificationParam.getName())
                .value(notificationParam.getValue())
                .build();
    }

    public static Notification toNotification(NotificationEntity notificationEntity){
        List<NotificationParam> notificationParams =
                notificationEntity.getParams().stream().map(NotificationMapper::toNotificationParamEntity).collect(Collectors.toList());

         Notification notification = Notification.builder()
                 .id(notificationEntity.getId())
                 .channel(notificationEntity.getChannel())
                 .type(notificationEntity.getType())
                 .createdById(notificationEntity.getCreatedById())
                 .createdOn(Instant.parse(notificationEntity.getCreatedOn()))
                 .params(notificationParams)
                 .receiver(notificationEntity.getReceiver())
                 .send(false)
                 .build();
        return notification;

    }

    public static NotificationParam toNotificationParamEntity(NotificationParamEntity notificationParamEntity){
        return NotificationParam.builder()
                .name(notificationParamEntity.getName())
                .id(notificationParamEntity.getId())
                .value(notificationParamEntity.getValue())
                .build();
    }
}
