package com.sensilabs.projecthub.notification.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationParam {
    private String id;
    private String name;
    private String value;
}
