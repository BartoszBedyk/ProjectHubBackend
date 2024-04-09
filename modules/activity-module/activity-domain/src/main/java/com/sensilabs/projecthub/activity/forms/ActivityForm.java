package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;

import java.util.List;
import java.util.Map;

public interface ActivityForm {
    ActivityType getType();
    Map<String, String> getParams();
}
