package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;

import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);

    SearchResponse<Project> search(SearchForm searchForm);

    Optional<Project> findById(String id);
}
