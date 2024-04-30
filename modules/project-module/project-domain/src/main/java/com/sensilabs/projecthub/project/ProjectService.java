package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;

public interface ProjectService {
    Project save(CreateProjectForm createProjectForm, String id);

    Project getById(String id);

    Project update(UpdateProjectForm updateProjectForm);

    SearchResponse<Project> search(SearchForm searchForm);
}
