package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.List;

@EnableScheduling
@Configuration
public class MailScheluder {

    private final EmailingService emailingService;
    private final NotificationService notificationService;

    @Autowired
    public MailScheluder(EmailingService emailingService, NotificationService notificationService) {
        this.emailingService = emailingService;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedDelay = 180000)
    public void scheduledMailing() throws InterruptedException {
        Instant time = Instant.now();
        List<Notification> notSent = notificationService.findAllMailBySentAndLastAttemptOnAndNumberOfAttempts(false, time, 5);
        if (notSent.isEmpty()) {
            System.out.println("No emails to send.");
        } else {
            for (Notification notification : notSent) {
                try {
                    emailingService.send(notification);
                } catch (Exception e) {
                    System.out.println("Unable to send mail to " + notification.getReceiver());
                }
            }
        }
    }
}
