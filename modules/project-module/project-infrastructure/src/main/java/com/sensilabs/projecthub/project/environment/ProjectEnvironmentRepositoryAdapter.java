package com.sensilabs.projecthub.project.environment;

import com.sensilabs.projecthub.project.environment.repository.ProjectEnvironmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectEnvironmentRepositoryAdapter implements ProjectEnvironmentRepository {

    private final ProjectEnvironmentRepositoryJpa projectEnvironmentRepositoryJpa;

    public ProjectEnvironmentRepositoryAdapter(ProjectEnvironmentRepositoryJpa projectEnvironmentRepositoryJpa) {
        this.projectEnvironmentRepositoryJpa = projectEnvironmentRepositoryJpa;
    }

    @Override
    public void save(ProjectEnvironment projectEnvironment) {
        ProjectEnvironmentEntity projectEnvironmentEntity = ProjectEnvironmentMapper.toProjectEnvironmentEntity(projectEnvironment);
        projectEnvironmentRepositoryJpa.save(projectEnvironmentEntity);
    }

    @Override
    public Optional<ProjectEnvironment> findById(String id) {
        Optional<ProjectEnvironmentEntity> projectEnvironmentEntity = projectEnvironmentRepositoryJpa.findById(id);
        return projectEnvironmentEntity.map(ProjectEnvironmentMapper::toProjectEnvironment);
    }

    @Override
    public List<ProjectEnvironment> findAllNotDeleted(String projectId) {
        List<ProjectEnvironmentEntity> entityList = projectEnvironmentRepositoryJpa.findAllEnvsByProjectIdAndDeletedOnIsNull(projectId);
        return entityList.stream()
                .map(ProjectEnvironmentMapper::toProjectEnvironment)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectEnvironment> findAllByIds(List<String> ids) {
        List<ProjectEnvironmentEntity> entityList = projectEnvironmentRepositoryJpa.findAllByIdIn(ids);
        return entityList.stream()
                .map(ProjectEnvironmentMapper::toProjectEnvironment)
                .collect(Collectors.toList());
    }
}
