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

    private String id;

    private NotificationType type;

    private List<NotificationParam> params;

    private Instant createdOn;

    private String createdById;

    private String receiver;

    private NotificationChannel channel;

    private Instant lastAttemptOn;

    private Boolean send;

    private Integer numberOfAttempts;


    public void  increaseAttempts(){
        this.lastAttemptOn = Instant.now();
        this.numberOfAttempts += 1;
    }

    public void finalizeSent() {
        this.send = true;
    }
}
