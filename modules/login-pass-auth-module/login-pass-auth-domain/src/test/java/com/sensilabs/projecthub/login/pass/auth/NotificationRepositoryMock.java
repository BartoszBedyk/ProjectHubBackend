package com.sensilabs.projecthub.login.pass.auth;

import com.sensilabs.projecthub.notification.NotificationRepository;
import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import com.sensilabs.projecthub.notification.model.Notification;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class NotificationRepositoryMock implements NotificationRepository {
    @Override
    public Notification save(Notification notification) {
        return null;
    }

    @Override
    public Optional<Notification> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Notification> findAllBySentAndLastAttemptedAndNumberOfAttempts(boolean sent, Instant time, int numberOfAttempts, NotificationChannel channel) {
        return null;
    }
}
