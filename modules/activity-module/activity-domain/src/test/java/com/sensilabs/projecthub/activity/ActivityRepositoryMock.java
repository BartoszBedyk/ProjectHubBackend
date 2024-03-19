package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.activity.ActivityRepository;

import java.util.HashMap;
import java.util.Map;

public class ActivityRepositoryMock implements ActivityRepository{

    Map<String, Activity> mockDB = new HashMap<>();
    @Override
    public Activity save(Activity activity) {
        mockDB.put(activity.getId(), activity);
        return activity;
    }
}
