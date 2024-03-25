package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;

import java.util.ArrayList;
import java.util.List;

public class LogInSuccessUserForm implements ActivityForm{

    private String userId;
    private String firstName;
    private String lastName;

    private enum Fields{
        USER_ID,
        FIRST_NAME,
        LAST_NAME
    }
    @Override
    public ActivityType getType() { return ActivityType.USER_LOG_IN_SUCCESS ;}

    @Override
    public List<ActivityParam> getParams() {
        List<ActivityParam> params = new ArrayList<>();
        params.add(new ActivityParam(Fields.USER_ID.name(), userId, ActivityType.USER_LOG_IN_SUCCESS));
        params.add(new ActivityParam(Fields.FIRST_NAME.name(), firstName, ActivityType.USER_LOG_IN_SUCCESS));
        params.add(new ActivityParam(Fields.LAST_NAME.name(), lastName, ActivityType.USER_LOG_IN_SUCCESS));
        return params;
    }
}
