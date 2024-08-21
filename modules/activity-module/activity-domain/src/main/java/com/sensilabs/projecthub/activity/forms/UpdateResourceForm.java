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
public class UpdateResourceForm implements ActivityForm {

    @NotNull(message = "Resource ID cannot be null.")
    @Size(min = 36, max = 36, message = "Resource ID must have 36 characters.")
    private String resourceId;
    @NotBlank
    private String resourceType;

    private enum Fields {
        RESOURCE_ID,
        RESOURCE_TYPE
    }

    @Override
    public ActivityType getType() {
        return ActivityType.UPDATE_RESOURCE;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Fields.RESOURCE_ID.name(), resourceId);
        params.put(Fields.RESOURCE_TYPE.name(), resourceType);
        return params;
    }
}
