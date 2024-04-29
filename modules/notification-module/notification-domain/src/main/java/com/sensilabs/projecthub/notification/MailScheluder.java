package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.List;

@EnableScheduling
@Configuration
public class MailScheluder {

    private final NotificationRepository notificationRepository;
    private final EmailingService emailingService;

    public MailScheluder(NotificationRepository notificationRepository, EmailingService emailingService) {
        this.notificationRepository = notificationRepository;
        this.emailingService = emailingService;
    }

    @Scheduled(fixedDelay = 180000)
    public void schelduledMailing() throws InterruptedException{
        Instant time = Instant.now();
        List<Notification> notSent= notificationRepository.findAllBySentAndLastAttemptedAndNumberOfAttempts(false,time,5 );
        if(notSent.isEmpty()){
            System.out.println("No emails to send.");
        }
        else {
            for(Notification notification : notSent ) {
                try{
                    emailingService.send(notification);
                }
                catch(Exception e){
                    System.out.println("Unable to send mail to" + notification.getReceiver());
                }
            }
        }
    }
}
