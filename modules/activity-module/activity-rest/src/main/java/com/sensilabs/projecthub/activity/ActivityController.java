package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.*;
import com.sensilabs.projecthub.activity.model.Activity;
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

    @PostMapping
    public Activity saveActivity(@RequestBody CreateUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/createUser")
    public Activity saveCreateUser(@RequestBody CreateUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/deleteUser")
    public Activity saveDeleteUser(@RequestBody DeleteUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/documentDownload")
    public Activity saveDocumentDownload(@RequestBody DocumentDownloadForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/documentOpen")
    public Activity saveDocumentOpen(@RequestBody DocumentOpenForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/keyOpen")
    public Activity saveKeyOpen(@RequestBody KeyOpenForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/logInFailed")
    public Activity saveLogInFailed(@RequestBody LogInFailedUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/logInSuccess")
    public Activity saveLogInSuccess(@RequestBody LogInSuccessUserForm activity) {return activityService.save(activity, activity.getUserId());}

    @PostMapping("/logOut")
    public Activity saveLogOut(@RequestBody LogOutUserForm activity) {return activityService.save(activity, activity.getUserId());}

}
