package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.forms.ActivityForm;
import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ActivityService {

    Activity save(@Valid ActivityForm form, String createdById);
    SearchResponse<Activity> search(SearchForm searchForm);
}
