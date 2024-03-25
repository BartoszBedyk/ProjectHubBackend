package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.activity.model.ActivityParam;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityMapper {
    public static ActivityEntity toActivityEntity(Activity activity) {
        List<ActivityParamEntity> activityParamEntities = activity.getParams().stream()
                .map(ActivityMapper::toActivityParamEntity)
                .collect(Collectors.toList());

        return ActivityEntity.builder()
                .id(activity.getId())
                .createdById(activity.getCreatedById())
                .type(activity.getType())
                .createdOn(activity.getCreatedOn())
                .params(activityParamEntities)
                .build();
    }

    private static ActivityParamEntity toActivityParamEntity(ActivityParam activityParam) {
        return ActivityParamEntity.builder()
                .paramName(activityParam.getName())
                .paramValue(activityParam.getValue())
                .build();
    }

    public static Activity toActivity(ActivityEntity activityEntity) {
        List<ActivityParam> activityParams = activityEntity.getParams().stream()
                .map(ActivityMapper::toActivityParam)
                .collect(Collectors.toList());

        return Activity.builder()
                .id(activityEntity.getId())
                .createdById(activityEntity.getCreatedById())
                .type(activityEntity.getType())
                .createdOn(activityEntity.getCreatedOn())
                .params(activityParams)
                .build();
    }

    private static ActivityParam toActivityParam(ActivityParamEntity activityParamEntity) {
        return ActivityParam.builder()
                .name(activityParamEntity.getParamName())
                .value(activityParamEntity.getParamValue())
                .build();
    }
}
