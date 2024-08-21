package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeleteProjectEnvironmentForm implements ActivityForm {

    @NotNull(message = "Project ID cannot be null.")
    @Size(min = 36, max = 36, message = "Project ID must have 36 characters.")
    private String projectId;
    @NotBlank(message = "Environment ID cannot be blank.")
    @Size(min = 36, max = 36, message = "Environment ID must have 36 characters.")
    private String environmentId;

    private enum Fields {
        PROJECT_ID,
        ENVIRONMENT_ID
    }

    @Override
    public ActivityType getType() {
        return ActivityType.DELETE_PROJECT_ENVIRONMENT;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Fields.PROJECT_ID.name(), projectId);
        params.put(Fields.ENVIRONMENT_ID.name(), environmentId);
        return params;
    }
}
