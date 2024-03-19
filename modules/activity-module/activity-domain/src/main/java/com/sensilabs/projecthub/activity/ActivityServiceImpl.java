package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.ActivityForm;
import com.sensilabs.projecthub.activity.model.Activity;

import java.util.Date;
import java.util.UUID;

public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository)
    {
        this.activityRepository = activityRepository;
    }
    @Override
    public Activity save(ActivityForm form) {
        Date currentDate = new Date();

        Activity activity = Activity.builder()
                .id(UUID.randomUUID().toString())
                .createdById("TODO")
                .type(form.getType())
                .createdOn(currentDate)
                .params(form.getParams())
                .build();

        return activityRepository.save(activity);
    }
}
