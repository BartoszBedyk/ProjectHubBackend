package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.EmailingService;
import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import com.sensilabs.projecthub.notification.model.Notification;
import com.sensilabs.projecthub.notification.model.NotificationParam;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EmailingServiceImpl implements EmailingService {


    private final EmailSender emailSender;

    @Value("{spring.mail.username}")
    private String fromMail;

    public void send(Notification notification) {
        try {
            emailSender.send(notification.getReceiver(), notification.getType().getSubject(), EmailingServiceImpl.toMap(notification.getParams()),
                    notification.getType().getTemplateId());
            notification.setLastAttemptOn(Instant.now());
            notification.setSend(true);
            System.out.println("Mail został wysłany!");
        } catch (Exception e) {
            System.out.println("Notification variable Send is true");
            System.out.println("notification receiver" + notification.getReceiver());
            System.out.println("notification Subject" + notification.getType().getSubject());
            System.out.println("notification temlate id" + notification.getType().getTemplateId());
            System.out.println("paramater 1 : " + notification.getParams().get(0));
            System.out.println("paramater 2 : " + notification.getParams().get(1));
        }

    }

    public static Map<String,String> toMap(List<NotificationParam> list){
        Map<String, String> paramsMap = new HashMap<>();
        for(NotificationParam param : list)
        {
            paramsMap.put(param.getName(), param.getValue());
        }
        return paramsMap;
    }


}
