package com.sensilabs.projecthub.notification.forms;

import com.sensilabs.projecthub.notification.model.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class AccountCreatedSmsForm implements NotificationForm {

    @NotNull(message = "First name cannot be null.")
    @NotBlank(message = "First name cannot be blank.")
    @Length(min = 2, max = 20, message = "Length of first name must be between 2 and 20.")
    private String firstName;


    @NotNull(message = "Last name cannot be null.")
    @NotBlank(message = "Last name cannot be blank.")
    @Length(min = 2, max = 50, message = "Length of first name must be between 2 and 50.")
    private String lastName;

    @Size(max = 9, min = 9, message = "Phone number must contains 9 numbers.")
    @NotNull(message = "Phone number cannot be null.")
    @NotBlank(message = "Phone number cannot be blank.")
    private String phone;


    @Override
    public NotificationType getType() {
        return NotificationType.ACCOUNT_CREATE;
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("FIRST_NAME", firstName);
        params.put("LAST_NAME", lastName);
        return params;
    }

    @Override
    public String getReceiver() {
        return this.phone;
    }


}
