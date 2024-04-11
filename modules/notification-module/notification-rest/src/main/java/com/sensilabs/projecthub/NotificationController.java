package com.sensilabs.projecthub;


import com.sensilabs.projecthub.notification.NotificationService;
import com.sensilabs.projecthub.notification.forms.AccountCreatedMailForm;
import com.sensilabs.projecthub.notification.model.Notification;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/notification/test")
public class NotificationController {
    private final NotificationService notificationService;


    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public Notification save(@RequestBody AccountCreatedMailForm accountCreatedMailForm) {
        String std = UUID.randomUUID().toString();
        return notificationService.save(accountCreatedMailForm, std);
    }

    @GetMapping(value = "{id}")
    public Optional<Notification> findById(@PathVariable("id") String id){
        return notificationService.findById(id);
    }

}
