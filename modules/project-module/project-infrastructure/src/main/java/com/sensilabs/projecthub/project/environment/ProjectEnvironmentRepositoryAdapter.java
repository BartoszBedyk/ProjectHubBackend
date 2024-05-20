package com.sensilabs.projecthub.project.environment;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.project.environment.repository.ProjectEnvironmentRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<ProjectEnvironment> findAll(String projectId) {
        Optional<List<ProjectEnvironmentEntity>> optionalEntityList = projectEnvironmentRepositoryJpa.findByProjectId(projectId);
        if (optionalEntityList.isPresent()) {
            List<ProjectEnvironmentEntity> entityList = optionalEntityList.get();
            List<ProjectEnvironment> modelList = new ArrayList<>();
            for (ProjectEnvironmentEntity entity : entityList) {
                ProjectEnvironment model = ProjectEnvironmentMapper.toProjectEnvironment(entity);
                modelList.add(model);
            }
        return modelList;
        } else {
            throw new ApplicationException(ErrorCode.PROJECT_NOT_FOUND);
        }
    }
}
