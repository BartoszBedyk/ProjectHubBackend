package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.model.Notification;
import com.sensilabs.projecthub.notification.model.NotificationParam;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {
    public static NotificationEntity toNotificationEntity(Notification notification){
        List<NotificationParamEntity> notificationParamEntities =
                notification.getParams().stream().map(NotificationMapper::toNotificationParam).collect(Collectors.toList());

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .id(notification.getId())
                .channel(notification.getChannel())
                .type(notification.getType())
                .createdBy(notification.getCreatedBy())
                .createdOn(notification.getCreatedOn().toString())
                .params(notificationParamEntities)
                .build();

        return notificationEntity;
    }

    private static NotificationParamEntity toNotificationParam(NotificationParam notificationParam){
        return NotificationParamEntity.builder()
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
                 .createdBy(notificationEntity.getCreatedBy())
                 .createdOn(Instant.parse(notificationEntity.getCreatedOn()))
                 .params(notificationParams)
                 .build();
        return notification;

    }

    private static NotificationParam toNotificationParamEntity(NotificationParamEntity notificationParamEntity){
        return NotificationParam.builder()
                .name(notificationParamEntity.getName())
                .value(notificationParamEntity.getValue())
                .build();
    }
}
