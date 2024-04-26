package com.sensilabs.projecthub.notification;

import com.sensilabs.projecthub.notification.EmailingService;
import com.sensilabs.projecthub.notification.NotificationRepository;
import com.sensilabs.projecthub.notification.model.Notification;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.function.Consumer;

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

        List<Notification> notSent= notificationRepository.findNotSent();
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
