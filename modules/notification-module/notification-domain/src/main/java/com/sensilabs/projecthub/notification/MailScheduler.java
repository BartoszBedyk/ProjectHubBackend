package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@EnableScheduling
@Configuration
public class MailScheduler {

    private final EmailingService emailingService;
    private final NotificationService notificationService;
    private final NotificationProps notificationProps;

    @Autowired
    public MailScheduler(EmailingService emailingService, NotificationService notificationService, NotificationProps notificationProps) {
        this.emailingService = emailingService;
        this.notificationService = notificationService;
        this.notificationProps = notificationProps;
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduledMailing() throws InterruptedException {
        Instant now = Instant.now();
        Instant time = now.minus(notificationProps.nextMailAttemptDelayInSeconds(), ChronoUnit.SECONDS);

        List<Notification> notSent = notificationService.findAllMailBySentAndLastAttemptOnAndNumberOfAttempts(false, time, notificationProps.numberOfAttempts());
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
