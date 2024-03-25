package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.ActivityForm;
import com.sensilabs.projecthub.activity.model.Activity;

import java.time.Instant;
import java.util.UUID;

public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository)
    {
        this.activityRepository = activityRepository;
    }
    @Override
    public Activity save(ActivityForm form, String createdById) {

        Activity activity = Activity.builder()
                .id(UUID.randomUUID().toString())
                .createdById(createdById)
                .type(form.getType())
                .createdOn(Instant.now())
                .params(form.getParams())
                .build();


        return activityRepository.save(activity);
    }
}
