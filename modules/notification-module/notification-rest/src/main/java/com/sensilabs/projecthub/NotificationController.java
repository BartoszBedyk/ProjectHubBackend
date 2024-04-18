package com.sensilabs.projecthub;


import com.sensilabs.projecthub.notification.NotificationService;
import com.sensilabs.projecthub.notification.configurations.mail.EmailingService;
import com.sensilabs.projecthub.notification.forms.AccountCreatedMailForm;
import com.sensilabs.projecthub.notification.forms.AccountCreatedSmsForm;
import com.sensilabs.projecthub.notification.forms.ResetPasswordMailFrom;
import com.sensilabs.projecthub.notification.forms.ResetPasswordSmsForm;
import com.sensilabs.projecthub.notification.model.Notification;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final EmailingService emailingService;


    public NotificationController(NotificationService notificationService, EmailingService emailingService) {
        this.notificationService = notificationService;
        this.emailingService = emailingService;
    }

    @PostMapping("/create-account-mail")
    public Notification save(@RequestBody AccountCreatedMailForm form) throws MessagingException {
        String std = UUID.randomUUID().toString();
        Notification notification = notificationService.save(form, std);
        emailingService.sendMailFixed(notification);
        return notification;
    }

    @PostMapping("/create-account-sms")
    public Notification save(@RequestBody AccountCreatedSmsForm form) throws MessagingException {
        String std = UUID.randomUUID().toString();
        Notification notification = notificationService.save(form, std);
        emailingService.sendMailFixed(notification);
        return notification;
    }

    @PostMapping("/reset-password-mail")
    public Notification save(@RequestBody ResetPasswordMailFrom form) throws MessagingException {
        String std = UUID.randomUUID().toString();
        Notification notification = notificationService.save(form, std);
        emailingService.sendMailFixed(notification);
        return notification;
    }

    @PostMapping("/reset-password-sms")
    public Notification save(@RequestBody ResetPasswordSmsForm form) throws MessagingException {
        String std = UUID.randomUUID().toString();
        Notification notification = notificationService.save(form, std);
        emailingService.sendMailFixed(notification);
        return notification;
    }


    @GetMapping(value = "{id}")
    public Optional<Notification> findById(@PathVariable("id") String id) {
        return notificationService.findById(id);
    }


}
