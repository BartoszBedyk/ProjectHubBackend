package com.sensilabs.projecthub.project.environment.repository;

import com.sensilabs.projecthub.project.environment.ProjectEnvironment;

import java.util.List;
import java.util.Optional;

public interface ProjectEnvironmentRepository {
    ProjectEnvironment save(ProjectEnvironment projectEnvironment);
    Optional<ProjectEnvironment> findById(String id);
    List<ProjectEnvironment> findAllNotDeleted(String projectId);
    List<ProjectEnvironment> findAllByIds(List<String> ids);
}
