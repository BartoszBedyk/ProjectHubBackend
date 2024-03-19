package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.CreateUserForm;
import com.sensilabs.projecthub.activity.model.Activity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ActivityServiceTest {

    ActivityRepository activityRepository = new ActivityRepositoryMock();
    ActivityService activityService = new ActivityServiceImpl(activityRepository);

    @Test
    void saveActivity(){
        Activity activity = activityService.save(new CreateUserForm());
    }

}

