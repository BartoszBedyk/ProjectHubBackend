package com.sensilabs.projecthub.project.environment.repository;

import com.sensilabs.projecthub.project.environment.ProjectEnvironment;

import java.util.List;
import java.util.Optional;

public interface ProjectEnvironmentRepository {
    void save(ProjectEnvironment projectEnvironment);
    Optional<ProjectEnvironment> findById(String id);
    void delete(ProjectEnvironment projectEnvironment);
    List<ProjectEnvironment> findAll(String projectId);
}
