package com.sensilabs.projecthub.notification;


import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import com.sensilabs.projecthub.notification.forms.NotificationForm;
import com.sensilabs.projecthub.notification.model.Notification;
import com.sensilabs.projecthub.notification.model.NotificationParam;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {
    public final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    @Override
    public Notification save(NotificationForm notificationForm, String createdById) {

        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .type(notificationForm.getType())
                .channel(notificationForm.getChannel())
                .createdOn(Instant.now())
                .createdById(createdById)
                .receiver(notificationForm.getReceiver())
                .sent(false)
                .numberOfAttempts(0)
                .params(notificationForm.getParams()
                        .entrySet()
                        .stream()
                        .map(param -> new NotificationParam(UUID.randomUUID().toString(), param.getKey(), param.getValue()))
                        .toList())
                .link(notificationForm.getLink())
                .build();

        return notificationRepository.save(notification);

    }

    @Override
    public Optional<Notification> findById(String id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> findAllMailBySentAndLastAttemptOnAndNumberOfAttempts(boolean sent, Instant time, int numberOfAttempts) {
        return notificationRepository.findAllBySentAndLastAttemptedAndNumberOfAttempts(sent, time, numberOfAttempts, NotificationChannel.EMAIL);
    }

    @Override
    public List<Notification> findAllSMSBySentAndLastAttemptOnAndNumberOfAttempts(boolean sent, Instant time, int numberOfAttempts) {
        return notificationRepository.findAllBySentAndLastAttemptedAndNumberOfAttempts(sent, time, numberOfAttempts, NotificationChannel.SMS);
    }


}
