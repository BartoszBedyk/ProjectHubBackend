package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.model.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityRepositoryAdapter implements ActivityRepository{

    private final ActivityRepositoryJpa activityRepository;

    public ActivityRepositoryAdapter(ActivityRepositoryJpa activityRepository)
    {
        this.activityRepository = activityRepository;
    }
    @Override
    public Activity save(Activity activity) {
        ActivityEntity activitySave = ActivityMapper.toActivityEntity(activity);
        return ActivityMapper.toActivity(activityRepository.save(activitySave));
    }
}
