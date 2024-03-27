package com.sensilabs.projecthub.notification.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationParam {
    private String name;
    private String value;
    //private Priority priority;
}
