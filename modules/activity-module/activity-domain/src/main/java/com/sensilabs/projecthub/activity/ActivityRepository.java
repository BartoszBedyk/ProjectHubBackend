package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.model.Activity;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;

import java.util.List;

public interface ActivityRepository {

    Activity save(Activity activity);
    SearchResponse<Activity> search(SearchForm searchForm);
}
