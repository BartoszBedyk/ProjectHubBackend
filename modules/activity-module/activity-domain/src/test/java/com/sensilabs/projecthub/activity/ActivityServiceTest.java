package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.*;
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
    void createUserTest() {
        Instant beforeDate = Instant.now();
        Activity activity = activityService.save(new CreateUserForm("1", "Kamil", "Smolarek"), "testUser");
        Instant afterDate = Instant.now();
        Assertions.assertEquals(activity.getCreatedById(), "testUser");
        Assertions.assertEquals(activity.getType(), ActivityType.CREATE_USER);
        List<ActivityParam> params = activity.getParams();
        Assertions.assertEquals(getParam(params, "USER_ID"), "1");
        Assertions.assertEquals(getParam(params, "FIRST_NAME"), "Kamil");
        Assertions.assertEquals(getParam(params, "LAST_NAME"), "Smolarek");
        Assertions.assertTrue(activity.getCreatedOn().isAfter(beforeDate) && activity.getCreatedOn().isBefore(afterDate));
    }

    @Test
    void deleteUserTest() {
        Instant beforeDate = Instant.now();
        Activity activity = activityService.save(new DeleteUserForm("2", "Michał", "Nowak"), "testUser");
        Instant afterDate = Instant.now();
        Assertions.assertEquals(activity.getCreatedById(), "testUser");
        Assertions.assertEquals(activity.getType(), ActivityType.DELETE_USER);
        List<ActivityParam> params = activity.getParams();
        Assertions.assertEquals(getParam(params, "USER_ID"), "2");
        Assertions.assertEquals(getParam(params, "FIRST_NAME"), "Michał");
        Assertions.assertEquals(getParam(params, "LAST_NAME"), "Nowak");
        Assertions.assertTrue(activity.getCreatedOn().isAfter(beforeDate) && activity.getCreatedOn().isBefore(afterDate));
    }

    @Test
    void loginFailedUserTest() {
        Instant beforeDate = Instant.now();
        Activity activity = activityService.save(new LogInFailedUserForm("3", "Piotr", "Lis"), "testUser");
        Instant afterDate = Instant.now();
        Assertions.assertEquals(activity.getCreatedById(), "testUser");
        Assertions.assertEquals(activity.getType(), ActivityType.USER_LOG_IN_FAILED);
        List<ActivityParam> params = activity.getParams();
        Assertions.assertEquals(getParam(params, "USER_ID"), "3");
        Assertions.assertEquals(getParam(params, "FIRST_NAME"), "Piotr");
        Assertions.assertEquals(getParam(params, "LAST_NAME"), "Lis");
        Assertions.assertTrue(activity.getCreatedOn().isAfter(beforeDate) && activity.getCreatedOn().isBefore(afterDate));
    }

    @Test
    void loginSuccessUserTest() {
        Instant beforeDate = Instant.now();
        Activity activity = activityService.save(new LogInSuccessUserForm("4", "Jan", "Kowalski"), "testUser");
        Instant afterDate = Instant.now();
        Assertions.assertEquals(activity.getCreatedById(), "testUser");
        Assertions.assertEquals(activity.getType(), ActivityType.USER_LOG_IN_SUCCESS);
        List<ActivityParam> params = activity.getParams();
        Assertions.assertEquals(getParam(params, "USER_ID"), "4");
        Assertions.assertEquals(getParam(params, "FIRST_NAME"), "Jan");
        Assertions.assertEquals(getParam(params, "LAST_NAME"), "Kowalski");
        Assertions.assertTrue(activity.getCreatedOn().isAfter(beforeDate) && activity.getCreatedOn().isBefore(afterDate));
    }

    @Test
    void logOutUserTest() {
        Instant beforeDate = Instant.now();
        Activity activity = activityService.save(new LogOutUserForm("5", "Adam", "Kaczkowski"), "testUser");
        Instant afterDate = Instant.now();
        Assertions.assertEquals(activity.getCreatedById(), "testUser");
        Assertions.assertEquals(activity.getType(), ActivityType.USER_LOG_OUT);
        List<ActivityParam> params = activity.getParams();
        Assertions.assertEquals(getParam(params, "USER_ID"), "5");
        Assertions.assertEquals(getParam(params, "FIRST_NAME"), "Adam");
        Assertions.assertEquals(getParam(params, "LAST_NAME"), "Kaczkowski");
        Assertions.assertTrue(activity.getCreatedOn().isAfter(beforeDate) && activity.getCreatedOn().isBefore(afterDate));
    }

    @Test
    void documentDownloadTest() {
        Instant beforeDate = Instant.now();
        Activity activity = activityService.save(new DocumentDownloadForm("1","1"), "testUser");
        Instant afterDate = Instant.now();
        Assertions.assertEquals(activity.getCreatedById(), "testUser");
        Assertions.assertEquals(activity.getType(), ActivityType.DOCUMENT_DOWNLOAD);
        List<ActivityParam> params = activity.getParams();
        Assertions.assertEquals(getParam(params, "USER_ID"), "1");
        Assertions.assertEquals(getParam(params, "DOCUMENT_ID"), "1");
        Assertions.assertTrue(activity.getCreatedOn().isAfter(beforeDate) && activity.getCreatedOn().isBefore(afterDate));
    }

    @Test
    void documentOpenTest() {
        Instant beforeDate = Instant.now();
        Activity activity = activityService.save(new DocumentOpenForm("2","2"), "testUser");
        Instant afterDate = Instant.now();
        Assertions.assertEquals(activity.getCreatedById(), "testUser");
        Assertions.assertEquals(activity.getType(), ActivityType.DOCUMENT_OPEN);
        List<ActivityParam> params = activity.getParams();
        Assertions.assertEquals(getParam(params, "USER_ID"), "2");
        Assertions.assertEquals(getParam(params, "DOCUMENT_ID"), "2");
        Assertions.assertTrue(activity.getCreatedOn().isAfter(beforeDate) && activity.getCreatedOn().isBefore(afterDate));
    }

    @Test
    void keyOpenTest() {
        Instant beforeDate = Instant.now();
        Activity activity = activityService.save(new KeyOpenForm("1","1"), "testUser");
        Instant afterDate = Instant.now();
        Assertions.assertEquals(activity.getCreatedById(), "testUser");
        Assertions.assertEquals(activity.getType(), ActivityType.KEY_OPEN);
        List<ActivityParam> params = activity.getParams();
        Assertions.assertEquals(getParam(params, "USER_ID"), "1");
        Assertions.assertEquals(getParam(params, "KEY_ID"), "1");
        Assertions.assertTrue(activity.getCreatedOn().isAfter(beforeDate) && activity.getCreatedOn().isBefore(afterDate));
    }

    private String getParam(List<ActivityParam> params, String paramName) {
        return params.stream().filter(param -> param.getName().equals(paramName)).findFirst().get().getValue();
    }
}

