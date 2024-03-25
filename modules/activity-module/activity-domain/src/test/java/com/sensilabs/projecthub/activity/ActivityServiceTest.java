package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.CreateUserForm;
import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

public class ActivityServiceTest {

    ActivityRepository activityRepository = new ActivityRepositoryMock();
    ActivityService activityService = new ActivityServiceImpl(activityRepository);

    @Test
    void saveActivity() {
        Instant beforeDate = Instant.now();
        Activity activity = activityService.save(new CreateUserForm("1", "Kamil", "Smolarek"), "testUser");
        Instant afterDate = Instant.now();
        Assertions.assertEquals(activity.getCreatedById(), "testUser");
        Assertions.assertEquals(activity.getType(), ActivityType.CREATE_USER);
        List<ActivityParam> params = activity.getParams();
        Assertions.assertEquals(getParam(params, "USER_ID"), "1");
        Assertions.assertEquals(params.get(1).getValue(), "Kamil");
        Assertions.assertEquals(params.get(2).getValue(), "Smolarek");
        Assertions.assertTrue(activity.getCreatedOn().isAfter(beforeDate) && activity.getCreatedOn().isBefore(afterDate));


    }

    private String getParam(List<ActivityParam> params, String paramName) {
        return params.stream().filter(param -> param.getName().equals(paramName)).findFirst().get().getValue();
    }
}

