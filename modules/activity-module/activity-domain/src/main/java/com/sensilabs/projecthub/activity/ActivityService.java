package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.ActivityForm;
import com.sensilabs.projecthub.activity.model.Activity;

public interface ActivityService {
    Activity save(ActivityForm form);
}
