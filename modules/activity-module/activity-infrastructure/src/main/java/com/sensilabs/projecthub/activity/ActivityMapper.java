package com.sensilabs.projecthub.activity;

import java.util.List;
import java.util.stream.Collectors;

import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.activity.model.ActivityParam;

public class ActivityMapper {
	public static ActivityEntity toActivityEntity(Activity activity) {
		return ActivityEntity.builder()
				.id(activity.getId())
				.createdById(activity.getCreatedById())
				.type(activity.getType())
				.createdOn(activity.getCreatedOn())
				.build();
	}

	public static ActivityParamEntity toActivityParam(ActivityParam activityParam, ActivityEntity activityEntity) {
		return ActivityParamEntity.builder()
				.id((activityParam.getId()))
				.activity(activityEntity)
				.paramName(activityParam.getName())
				.paramValue(activityParam.getValue())
				.build();
	}

	public static Activity toActivity(ActivityEntity activityEntity) {
		return Activity.builder()
				.id(activityEntity.getId())
				.createdById(activityEntity.getCreatedById())
				.type(activityEntity.getType())
				.params(activityEntity.getParams().stream().map(ActivityMapper::toActivityParamEntity).toList())
				.createdOn(activityEntity.getCreatedOn())
				.build();
	}

	public static ActivityParam toActivityParamEntity(ActivityParamEntity activityParamEntity) {
		return ActivityParam.builder()
				.id((activityParamEntity.getId()))
				.name(activityParamEntity.getParamName())
				.value(activityParamEntity.getParamValue())
				.build();
	}
}
