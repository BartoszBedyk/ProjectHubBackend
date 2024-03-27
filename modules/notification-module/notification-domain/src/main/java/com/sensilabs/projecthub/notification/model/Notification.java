package com.sensilabs.projecthub.notification.model;

import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import lombok.*;

import java.time.Instant;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Notification {
    private String id;
    private NotificationType type;
    private List<NotificationParam> params;
    private Instant createdOn;
    private String createdBy;
    private String receiver;
    private NotificationChannel channel;


}
