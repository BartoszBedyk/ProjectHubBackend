package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;

import java.util.ArrayList;
import java.util.List;

public class KeyOpenForm implements ActivityForm{

    private String userId;
    private String keyId;

    private enum Fields{
        USER_ID,
        KEY_ID
    }
    @Override
    public ActivityType getType() { return ActivityType.KEY_OPEN ;}

    @Override
    public List<ActivityParam> getParams() {
        List<ActivityParam> params = new ArrayList<>();
        params.add(new ActivityParam(Fields.USER_ID.name(), userId, ActivityType.KEY_OPEN));
        params.add(new ActivityParam(Fields.KEY_ID.name(), keyId, ActivityType.KEY_OPEN));
        return params;
    }
}
