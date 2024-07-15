package com.sensilabs.projecthub.login.pass.auth;

import com.sensilabs.projecthub.activity.ActivityRepository;
import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;

import java.util.HashMap;
import java.util.Map;

public class ActivityRepositoryMock implements ActivityRepository {

    Map<String, Activity> mockDB = new HashMap<>();
    @Override
    public Activity save(Activity activity) {
        mockDB.put(activity.getId(), activity);
        return activity;
    }

    @Override
    public SearchResponse<Activity> search(SearchForm searchForm) {
        return null;
    }
}

