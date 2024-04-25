package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
public interface EmailingService {
    public void send(Notification notification) throws MailException;


}
