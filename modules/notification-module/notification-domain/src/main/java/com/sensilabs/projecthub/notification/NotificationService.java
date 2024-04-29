package com.sensilabs.projecthub.notification;


import com.sensilabs.projecthub.notification.forms.NotificationForm;
import com.sensilabs.projecthub.notification.model.Notification;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Validated
public interface NotificationService {

    Notification save(@Valid NotificationForm notificationForm, String createdById);
    Optional<Notification> findById(String id);
    List<Notification> findAllBySentAndLastAttemptOnAndNumberOfAttempts(boolean sent, Instant time, int numberOfAttempts);



}

