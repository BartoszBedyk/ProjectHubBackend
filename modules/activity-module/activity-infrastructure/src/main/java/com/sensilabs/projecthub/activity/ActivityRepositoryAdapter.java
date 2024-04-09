package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.model.Activity;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sensilabs.projecthub.activity.ActivityMapper.toActivityParam;


@Component
public class ActivityRepositoryAdapter implements ActivityRepository{

    private final ActivityRepositoryJpa activityRepository;
    private final ActivityParamRepositoryJpa activityParamRepository;

    public ActivityRepositoryAdapter(ActivityRepositoryJpa activityRepository, ActivityParamRepositoryJpa activityParamRepository)
    {
        this.activityRepository = activityRepository;
        this.activityParamRepository = activityParamRepository;
    }
    @Override
    public Activity save(Activity activity) {
        ActivityEntity activityEntity = ActivityMapper.toActivityEntity(activity);
        activityRepository.save(activityEntity);
        List<ActivityParamEntity> activityParamEntities =
                activity.getParams().stream().map(activityParam -> toActivityParam(activityParam, activityEntity)).toList();
        activityParamRepository.saveAll(activityParamEntities);
        activityEntity.setParams(activityParamEntities);
        return ActivityMapper.toActivity(activityEntity);
    }
}
