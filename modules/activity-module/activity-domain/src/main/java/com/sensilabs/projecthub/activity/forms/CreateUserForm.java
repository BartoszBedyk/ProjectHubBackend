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
public class CreateUserForm implements ActivityForm{

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
        return ActivityType.CREATE_USER ;
    }

    @Override
    public List<ActivityParam> getParams() {
        List<ActivityParam> params = new ArrayList<>();
        params.add(new ActivityParam(Fields.USER_ID.name(), userId, ActivityType.CREATE_USER));
        params.add(new ActivityParam(Fields.FIRST_NAME.name(), firstName, ActivityType.CREATE_USER));
        params.add(new ActivityParam(Fields.LAST_NAME.name(), lastName, ActivityType.CREATE_USER));
        return params;
    }
}
