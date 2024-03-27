package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.CreateUserForm;
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

//    @PostMapping
//    public Activity saveActivity(@RequestBody CreateUserForm activity) {return activityService.save(activity);}

}
