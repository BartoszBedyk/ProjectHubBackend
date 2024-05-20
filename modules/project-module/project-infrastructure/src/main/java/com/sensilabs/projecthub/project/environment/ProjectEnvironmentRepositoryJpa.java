package com.sensilabs.projecthub.project.environment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectEnvironmentRepositoryJpa extends JpaRepository<ProjectEnvironmentEntity, String> {
    List<ProjectEnvironmentEntity> findAllEnvsByProjectIdAndDeletedOnIsNull(String projectId);
}
