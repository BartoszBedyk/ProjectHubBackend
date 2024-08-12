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
        Notification notification = notificationService.save(new ResetPasswordMailFrom("Adam","Kowalczyk","AK@sensilabs.com",  "link"), "Admin");
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(notification.getCreatedById(), "Admin");
        Assertions.assertTrue(notification.getCreatedOn().isAfter(beforeDate) && notification.getCreatedOn().isBefore(afterDate));
        List<NotificationParam> params = notification.getParams();

        Assertions.assertEquals(getParam(params, "LAST_NAME"), "Kowalczyk");
        Assertions.assertEquals(getParam(params, "FIRST_NAME"), "Adam");

        Assertions.assertEquals(notification.getType(), NotificationType.PASSWORD_RESET);
        Assertions.assertEquals(notification.getChannel(), NotificationChannel.EMAIL);
        Assertions.assertEquals(notification.getNumberOfAttempts(), 0);
        Assertions.assertNull(notification.getLastAttemptOn());
        Assertions.assertEquals(notification.getSent(), false);

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
        Assertions.assertEquals(getParam(params, "LAST_NAME"), "Bekas");
        Assertions.assertEquals(getParam(params, "FIRST_NAME"), "Dawid");
        Assertions.assertEquals(notification.getType(), NotificationType.PASSWORD_RESET);
        Assertions.assertEquals(notification.getChannel(), NotificationChannel.SMS);
        Assertions.assertEquals(notification.getNumberOfAttempts(), 0);
        Assertions.assertNull(notification.getLastAttemptOn());
        Assertions.assertEquals(notification.getSent(), false);


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
        Assertions.assertEquals(getParam(params, "LAST_NAME"), "Witaj");
        Assertions.assertEquals(getParam(params, "FIRST_NAME"), "Anna");
        Assertions.assertEquals(notification.getType(), NotificationType.ACCOUNT_CREATE);
        Assertions.assertEquals(notification.getChannel(), NotificationChannel.EMAIL);
        Assertions.assertEquals(notification.getNumberOfAttempts(), 0);
        Assertions.assertNull(notification.getLastAttemptOn());
        Assertions.assertEquals(notification.getSent(), false);


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
        Assertions.assertEquals(getParam(params, "LAST_NAME"), "Bogusz");
        Assertions.assertEquals(getParam(params, "FIRST_NAME"), "Mirosława");
        Assertions.assertEquals(notification.getType(), NotificationType.ACCOUNT_CREATE);
        Assertions.assertEquals(notification.getChannel(), NotificationChannel.SMS);
        Assertions.assertEquals(notification.getNumberOfAttempts(), 0);
        Assertions.assertNull(notification.getLastAttemptOn());
        Assertions.assertEquals(notification.getSent(), false);
    }

    private String getParam(List<NotificationParam> params, String paramName) {
        return params.stream().filter(param -> param.getName().equals(paramName)).findFirst().get().getValue();
    }
}
