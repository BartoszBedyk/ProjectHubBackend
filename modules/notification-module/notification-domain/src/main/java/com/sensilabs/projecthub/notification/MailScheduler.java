package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

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
        final ExecutorService executorService = Executors.newFixedThreadPool(100);
        Instant now = Instant.now();
        Instant time = now.minus(notificationProps.nextMailAttemptDelayInSeconds(), ChronoUnit.SECONDS);

        List<Notification> notSent = notificationService.findAllMailBySentAndLastAttemptOnAndNumberOfAttempts(false, time, notificationProps.numberOfAttempts());
        if (notSent.isEmpty()) {
            System.out.println("No emails to send.");
        } else {
            for (Notification notification : notSent) {
                try {
                    executorService.submit(new MailSender(notification));
                } catch (RejectedExecutionException e) {
                    System.err.println("Task submission was rejected: " + e.getMessage());

                }finally {
                    executorService.shutdown();
                }
            }
        }
    }

    public class MailSender implements Runnable {

        private final Notification notification;

        public MailSender(Notification notification) {
            this.notification = notification;
        }
        @Override
        public void run() {
            try {
                emailingService.send(notification);
            } catch (Exception e) {
                System.out.println("Unable to send mail to " + notification.getReceiver());
            }

        }
    }


}
