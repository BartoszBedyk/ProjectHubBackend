package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.model.Notification;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findById(String id);
    List<Notification> findAllBySentAndLastAttemptedAndNumberOfAttempts(boolean sent, Instant time, int numberOfAttempts);




}
