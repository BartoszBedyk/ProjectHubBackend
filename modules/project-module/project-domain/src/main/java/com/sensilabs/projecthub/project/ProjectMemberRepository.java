package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository {
    ProjectMember save(ProjectMember projectMember);

    Optional<ProjectMember> findById(String userId, String projectId);

    void delete(ProjectMember projectMember);

    List<ProjectMember> findAllByProjectId(String projectId);
}
