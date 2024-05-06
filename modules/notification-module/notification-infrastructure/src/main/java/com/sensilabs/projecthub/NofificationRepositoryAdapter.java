package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.NotificationRepository;
import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sensilabs.projecthub.NotificationMapper.toNotificationParam;

@Component

public class NofificationRepositoryAdapter implements NotificationRepository {

    private final NotificationRepositoryJpa notificationRepositoryJpa;
    private final NotificationParamRepositoryJpa notificationParamRepositoryJpa;


    public NofificationRepositoryAdapter(NotificationRepositoryJpa notificationRepositoryJpa, NotificationParamRepositoryJpa notificationParamRepositoryJpa) {
        this.notificationRepositoryJpa = notificationRepositoryJpa;
        this.notificationParamRepositoryJpa = notificationParamRepositoryJpa;
    }

    @Override
    public Notification save(Notification notification) {

        NotificationEntity notificationEntity = NotificationMapper.toNotificationEntity(notification);
        notificationRepositoryJpa.save(notificationEntity);
        List<NotificationParamEntity> notificationParamEntities =
                notification.getParams().stream()
                        .map(notificationParam -> toNotificationParam(notificationParam, notificationEntity))
                        .collect(Collectors.toList());
        notificationParamRepositoryJpa.saveAll(notificationParamEntities);
        notificationEntity.setParams(notificationParamEntities);
        return NotificationMapper.toNotification(notificationEntity);

    }

    @Override
    public Optional<Notification> findById(String id) {
        return notificationRepositoryJpa.findById(id).map(NotificationMapper::toNotification);

    }

    @Override
    public List<Notification> findAllBySentAndLastAttemptedAndNumberOfAttempts(boolean sent, Instant time, int numberOfAttempts, NotificationChannel channel) {

        return notificationRepositoryJpa.findAllBySentAndLastAttemptOnAndNumberOfAttemptsQuery(sent, time, numberOfAttempts, channel).stream().map(NotificationMapper::toNotification).toList();

    }




}
