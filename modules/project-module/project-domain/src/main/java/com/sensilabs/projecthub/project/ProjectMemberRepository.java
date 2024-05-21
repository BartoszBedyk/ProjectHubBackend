package com.sensilabs.projecthub.project;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository {
    ProjectMember save(ProjectMember projectMember);

    Optional<ProjectMember> findById(String userId, String projectId);

    void delete(ProjectMember projectMember);

    List<Object[]> getProjects(String userId);
}
