package com.sensilabs.projecthub.notification;


import com.sensilabs.projecthub.notification.forms.*;
import com.sensilabs.projecthub.notification.model.Notification;
import com.sensilabs.projecthub.notification.model.NotificationParam;
import com.sensilabs.projecthub.notification.model.NotificationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.time.Instant;
import java.util.List;

public class NotificationServiceTest {
    NotificationRepository notificationRepository = new NotificationRepositoryMock();
    NotificationService notificationService = new NotificationServiceImpl(notificationRepository);
    @Test
    public void createResetPasswordMail() throws InterruptedException {
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        Notification notification = notificationService.save(new ResetPasswordMailFrom("Adam","Kowalczyk","AK@sensilabs.com"), "Admin");
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(notification.getCreatedById(), "Admin");
        Assertions.assertTrue(notification.getCreatedOn().isAfter(beforeDate) && notification.getCreatedOn().isBefore(afterDate));
        List<NotificationParam> params = notification.getParams();

        Assertions.assertEquals(params.get(0).getValue(), "Adam");
        Assertions.assertEquals(params.get(1).getValue(), "Kowalczyk");
        Assertions.assertEquals(notification.getType(), NotificationType.PASSWORD_RESET);
        Assertions.assertEquals(notification.getChannel(), NotificationChannel.EMAIL);

    }

    @Test
    public void createResetPasswordSMS() throws InterruptedException {
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        Notification notification = notificationService.save(new ResetPasswordSmsForm("Dawid","Bekas","093321341"), "Admin");
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(notification.getCreatedById(), "Admin");
        Assertions.assertTrue(notification.getCreatedOn().isAfter(beforeDate) && notification.getCreatedOn().isBefore(afterDate));
        List<NotificationParam> params = notification.getParams();
        Assertions.assertEquals(params.get(0).getValue(), "Dawid");
        Assertions.assertEquals(params.get(1).getValue(), "Bekas");
        Assertions.assertEquals(notification.getType(), NotificationType.PASSWORD_RESET);
        Assertions.assertEquals(notification.getChannel(), NotificationChannel.SMS);


    }
    @Test
    public void createNotificationMail() throws InterruptedException {
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        Notification notification = notificationService.save(new AccountCreatedMailForm("Anna","Witaj","a.witak@softhard.pl"), "Admin");
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(notification.getCreatedById(), "Admin");
        Assertions.assertTrue(notification.getCreatedOn().isAfter(beforeDate) && notification.getCreatedOn().isBefore(afterDate));
        List<NotificationParam> params = notification.getParams();
        Assertions.assertEquals(params.get(0).getValue(), "Anna");
        Assertions.assertEquals(params.get(1).getValue(), "Witaj");
        Assertions.assertEquals(notification.getType(), NotificationType.ACCOUNT_CREATE);
        Assertions.assertEquals(notification.getChannel(), NotificationChannel.EMAIL);


    }
    @Test
    public void createNotificationSMS() throws InterruptedException {
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        Notification notification = notificationService.save(new AccountCreatedSmsForm("Mirosława","Bogusz","019234902"), "Admin");
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(notification.getCreatedById(), "Admin");
        Assertions.assertTrue(notification.getCreatedOn().isAfter(beforeDate) && notification.getCreatedOn().isBefore(afterDate));
        List<NotificationParam> params = notification.getParams();
        Assertions.assertEquals(params.get(0).getValue(), "Mirosława");
        Assertions.assertEquals(params.get(1).getValue(), "Bogusz");
        Assertions.assertEquals(notification.getType(), NotificationType.ACCOUNT_CREATE);
        Assertions.assertEquals(notification.getChannel(), NotificationChannel.SMS);


    }
}
