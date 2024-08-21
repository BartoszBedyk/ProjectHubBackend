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
public class UpdateProjectForm implements ActivityForm {

    @NotNull(message = "Project ID cannot be null.")
    @Size(min = 36, max = 36, message = "Project ID must have 36 characters.")
    private String projectId;

    private enum Fields {
        PROJECT_ID,
    }

    @Override
    public ActivityType getType() {
        return ActivityType.UPDATE_PROJECT;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Fields.PROJECT_ID.name(), projectId);
        return params;
    }
}
