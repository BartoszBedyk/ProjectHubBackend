package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogInSuccessUserForm implements ActivityForm {

    @NotNull(message = "User ID cannot be null.")
    @Size(min = 36, max = 36, message = "User ID must have 36 characters.")
    private String userId;
    @NotBlank(message = "First name cannot be blank.")
    @Length(min = 1, max = 30, message = "First name must be between 1 and 30 characters.")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank.")
    @Length(min = 1, max = 50, message = "Last name must be between 1 and 30 characters.")
    private String lastName;

    private enum Fields {
        USER_ID,
        FIRST_NAME,
        LAST_NAME
    }

    @Override
    public ActivityType getType() {
        return ActivityType.USER_LOG_IN_SUCCESS;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Fields.USER_ID.name(), userId);
        params.put(Fields.FIRST_NAME.name(), firstName);
        params.put(Fields.LAST_NAME.name(), lastName);
        return params;
    }
}
