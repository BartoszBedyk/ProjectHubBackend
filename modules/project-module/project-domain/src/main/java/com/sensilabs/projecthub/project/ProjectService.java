package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ProjectService {
    Project save(@Valid CreateProjectForm createProjectForm, String createdById);

    Project getById(String id);

    Project update(@Valid UpdateProjectForm updateProjectForm);

    SearchResponse<Project> search(SearchForm searchForm);
}
