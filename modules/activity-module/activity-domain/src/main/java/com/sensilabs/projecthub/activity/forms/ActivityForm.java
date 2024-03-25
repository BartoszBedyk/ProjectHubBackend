package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;

import java.util.List;

public interface ActivityForm {
    ActivityType getType();
    List<ActivityParam> getParams();
}
