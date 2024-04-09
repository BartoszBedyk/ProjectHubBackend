package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KeyOpenForm implements ActivityForm{

    @NotNull(message = "userId cannot be null")
    @Size(min = 36, max = 36, message = "userId must have 36 characters")
    private String userId;
    @NotNull(message = "keyId cannot be null")
    @Size(min = 36, max = 36, message = "keyId must have 36 characters")
    private String keyId;

    private enum Fields{
        USER_ID,
        KEY_ID
    }
    @Override
    public ActivityType getType() { return ActivityType.KEY_OPEN ;}

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Fields.USER_ID.name(), userId);
        params.put(Fields.KEY_ID.name(), keyId);
        return params;
    }
}
