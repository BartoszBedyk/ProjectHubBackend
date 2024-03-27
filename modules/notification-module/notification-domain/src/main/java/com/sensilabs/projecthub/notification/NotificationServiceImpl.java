package com.sensilabs.projecthub.notification;


import com.sensilabs.projecthub.notification.forms.NotificationForm;
import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
                .params(notificationForm.getParams())

                .channel(notificationForm.getChannel())
                .createdOn(Instant.now())
                .createdBy(createdById)
                .build();
        return notificationRepository.save(notification);
    }
}
