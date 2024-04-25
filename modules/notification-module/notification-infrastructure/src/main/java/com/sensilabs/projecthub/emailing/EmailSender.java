package com.sensilabs.projecthub.emailing;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public interface EmailSender {

    void send(String sendTo, String subject, Map<String, String> params, String templateId) throws MessagingException;
}
