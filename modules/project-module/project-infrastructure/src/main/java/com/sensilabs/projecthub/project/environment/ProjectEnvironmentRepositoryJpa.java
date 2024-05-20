package com.sensilabs.projecthub.project.environment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectEnvironmentRepositoryJpa extends JpaRepository<ProjectEnvironmentEntity, String> {
    Optional<List<ProjectEnvironmentEntity>> findByProjectId(String projectId);
}
