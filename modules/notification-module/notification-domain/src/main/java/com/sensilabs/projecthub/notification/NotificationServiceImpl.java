package com.sensilabs.projecthub.notification;


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
                .params(notificationForm.getParams()
                        .entrySet()
                        .stream()
                        .map(param -> new NotificationParam(UUID.randomUUID().toString(), param.getKey(), param.getValue()))
                        .toList())
                .channel(notificationForm.getChannel())
                .createdOn(Instant.now())
                .createdById(createdById)
                .receiver(notificationForm.getReceiver())
                .build();
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> findById(String id) {
        return notificationRepository.findById(id);
    }

    //public List<NotificationParam> mapParam()
}
