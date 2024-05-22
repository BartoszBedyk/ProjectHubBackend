package com.sensilabs.projecthub.login.pass.auth;

import com.sensilabs.projecthub.notification.EmailingService;
import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.mail.MailException;

public class EmailingServiceMock implements EmailingService {
    @Override
    public void send(Notification notification) throws MailException {

    }
}
