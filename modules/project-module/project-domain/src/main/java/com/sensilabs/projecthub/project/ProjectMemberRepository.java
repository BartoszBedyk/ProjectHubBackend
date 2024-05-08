package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;

import java.util.Optional;

public interface ProjectMemberRepository {
    ProjectMember save(ProjectMember projectMember);

    SearchResponse<ProjectMember> search(SearchForm searchForm);

    Optional<ProjectMember> findById(String id);

    void delete(ProjectMember projectMember);
}
