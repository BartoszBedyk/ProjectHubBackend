package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.*;
import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }
    @PostMapping("/create-user")
    public Activity saveCreateUser(@RequestBody CreateUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/delete-user")
    public Activity saveDeleteUser(@RequestBody DeleteUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/document-download")
    public Activity saveDocumentDownload(@RequestBody DocumentDownloadForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/document-open")
    public Activity saveDocumentOpen(@RequestBody DocumentOpenForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/key-open")
    public Activity saveKeyOpen(@RequestBody KeyOpenForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/log-in-failed")
    public Activity saveLogInFailed(@RequestBody LogInFailedUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/log-in-success")
    public Activity saveLogInSuccess(@RequestBody LogInSuccessUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/log-out")
    public Activity saveLogOut(@RequestBody LogOutUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/search")
    public SearchResponse<Activity> search(@RequestBody SearchForm searchForm) {return activityService.search(searchForm);}

}
