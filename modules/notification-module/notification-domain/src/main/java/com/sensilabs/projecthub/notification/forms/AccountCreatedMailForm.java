package com.sensilabs.projecthub.notification.forms;

import com.sensilabs.projecthub.notification.model.NotificationParam;
import com.sensilabs.projecthub.notification.model.NotificationType;
import com.sensilabs.projecthub.notification.model.Priority;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreatedMailForm implements  NotificationForm{

    private String firstName;
    private String lastName;
    private String email;



    @Override
    public NotificationType getType() {
        return NotificationType.ACCOUNT_CREATE;
    }

    @Override
    public NotificationChannel getChannel() {
        return  NotificationChannel.EMAIL;
    }

    @Override
    public List<NotificationParam> getParams() {
        List<NotificationParam> params = new ArrayList<NotificationParam>();
        params.add(new NotificationParam("FIRST_NAME", firstName));
        params.add(new NotificationParam("LAST_NAME", lastName));

        return params;
    }

    @Override
    public String getReceiver() {
        return this.email;//email
    }




}
