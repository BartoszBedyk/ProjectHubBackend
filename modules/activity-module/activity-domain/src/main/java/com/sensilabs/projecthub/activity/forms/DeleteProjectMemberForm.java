package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityType;
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
public class DeleteProjectMemberForm implements ActivityForm {

    @NotNull(message = "Project ID cannot be null.")
    @Size(min = 36, max = 36, message = "Project ID must have 36 characters.")
    private String projectId;
    @NotNull(message = "User ID cannot be null.")
    @Size(min = 36, max = 36, message = "User ID must have 36 characters.")
    private String userId;

    private enum Fields {
        PROJECT_ID,
        USER_ID
    }

    @Override
    public ActivityType getType() {
        return ActivityType.DELETE_PROJECT_MEMBER;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Fields.PROJECT_ID.name(), projectId);
        params.put(Fields.USER_ID.name(), userId);
        return params;
    }
}
