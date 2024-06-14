package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@EnableScheduling
@Configuration
@Slf4j
@ConditionalOnProperty(value = "app.notification.mail.enabled", havingValue = "true")
public class MailScheduler {

    private final EmailingService emailingService;
    private final NotificationService notificationService;
    private final NotificationProps notificationProps;
    private final ExecutorService executorService;


    @Autowired
    public MailScheduler(EmailingService emailingService, NotificationService notificationService, NotificationProps notificationProps) {
        this.emailingService = emailingService;
        this.notificationService = notificationService;
        this.notificationProps = notificationProps;
        this.executorService = Executors.newFixedThreadPool(notificationProps.numberOfThreadsAndMailPerThread());
    }
    @Scheduled(fixedDelayString = "${app.notification.schedulerDelayInMilliseconds}")
    public void scheduledMailing() {

        Instant now = Instant.now();
        Instant time = now.minus(notificationProps.nextMailAttemptDelayInSeconds(), ChronoUnit.SECONDS);
        List<Notification> notSent;
        notSent = notificationService.findAllMailBySentAndLastAttemptOnAndNumberOfAttempts(false, time, notificationProps.numberOfAttempts());
        System.out.println("NotSent size: " + notSent.size());

        if (notSent.isEmpty()) {
           log.info("No notifications found. Process skipped");
        } else {
            log.info("Notifications found. Processed: " + notSent.size());
            List<Future> toSend = new ArrayList<>();
            for (Notification notification : notSent) {
                try {
                    toSend.add(executorService.submit(() -> emailingService.send(notification)));
                } catch (RejectedExecutionException e) {
                    System.err.println("Task was rejected " + e.getMessage());
                }
            }
            toSend.forEach(item -> {
                try {
                    item.get();
                } catch (InterruptedException e) {
                    Thread.interrupted();
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
            log.info("Notifications sent: " + toSend.size());
        }
    }
}





