package com.sensilabs.projecthub.notification.model;

import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Validated
public class Notification {

    @NotNull(message = "Id must not be null")
    @NotBlank(message = "id must not be blank")
    private String id;

    @NotBlank(message = "Type must not be blank")
    private NotificationType type;

    @NotEmpty(message = "Notification Params must not be empty")
    private List<NotificationParam> params;

    @NotEmpty(message = "createdOn must not be empty")
    @NotNull(message = "createdOn must not be null")
    private Instant createdOn;

    @NotBlank(message = "createdById must not be blank")
    private String createdById;

    @NotBlank(message = "receiver must not be blank")
    private String receiver;

    @NotEmpty(message = "channel must not be empty")
    private NotificationChannel channel;


}
