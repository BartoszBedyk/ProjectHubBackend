package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogInFailedUserForm implements ActivityForm{

    private String userId;
    private String firstName;
    private String lastName;

    private enum Fields{
        USER_ID,
        FIRST_NAME,
        LAST_NAME
    }
    @Override
    public ActivityType getType() {
        return ActivityType.USER_LOG_IN_FAILED ;
    }

    @Override
    public List<ActivityParam> getParams() {
        List<ActivityParam> params = new ArrayList<>();
        params.add(new ActivityParam(Fields.USER_ID.name(), userId, ActivityType.USER_LOG_IN_FAILED));
        params.add(new ActivityParam(Fields.FIRST_NAME.name(), firstName, ActivityType.USER_LOG_IN_FAILED));
        params.add(new ActivityParam(Fields.LAST_NAME.name(), lastName, ActivityType.USER_LOG_IN_FAILED));
        return params;
    }
}
