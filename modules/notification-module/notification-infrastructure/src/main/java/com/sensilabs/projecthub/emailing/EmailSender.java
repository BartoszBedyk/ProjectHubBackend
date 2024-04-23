package com.sensilabs.projecthub.emailing;

import com.sensilabs.projecthub.notification.model.Notification;
import jakarta.mail.MessagingException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface EmailSender {

    void send(String sendTo, String subject, Map<String, String> params, String templateId) throws MessagingException;
}
