package com.sensilabs.projecthub.activity;

import java.time.Instant;
import java.util.UUID;

import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.activity.model.ActivityParam;
import org.springframework.stereotype.Service;

import com.sensilabs.projecthub.activity.forms.ActivityForm;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity save(ActivityForm form, String createdById) {

        Activity activity = Activity.builder()
                .id(UUID.randomUUID().toString())
                .createdById(createdById)
                .type(form.getType())
                .createdOn(Instant.now())
                .params(form.getParams()
                        .entrySet()
                        .stream()
                        .map(param -> new ActivityParam(UUID.randomUUID().toString(), param.getKey(), param.getValue()))
                        .toList())
                .build();

        return activityRepository.save(activity);
    }
}
