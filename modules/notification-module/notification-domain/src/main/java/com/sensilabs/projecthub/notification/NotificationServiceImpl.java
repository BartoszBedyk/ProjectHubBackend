package com.sensilabs.projecthub.notification;


import com.sensilabs.projecthub.notification.forms.NotificationForm;
import com.sensilabs.projecthub.notification.model.Notification;
import com.sensilabs.projecthub.notification.model.NotificationParam;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
                .build();
        
        return notificationRepository.save(notification);

    }

    @Override
    public Optional<Notification> findById(String id) {
        return notificationRepository.findById(id);
    }



}
