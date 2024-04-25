package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.EmailingService;
import com.sensilabs.projecthub.notification.NotificationRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
@EnableScheduling
@Configuration
public class ScheludedTask {

    private final NotificationRepository notificationRepository;
    private final EmailingService emailingService;

    public ScheludedTask(NotificationRepository notificationRepository, EmailingService emailingService) {
        this.notificationRepository = notificationRepository;
        this.emailingService = emailingService;
    }

    @Scheduled(fixedDelay = 10000)
    public void schelduledMailing() throws InterruptedException{
        if(notificationRepository.findNotSent().isEmpty()){
            System.out.println("No emails to send.");
        }
        else {
            try{
                notificationRepository.findNotSent().forEach(emailingService::send);
            }
            catch(Exception e){
                System.out.println("No emails to send on the queue.");
            }
        }
    }
}
