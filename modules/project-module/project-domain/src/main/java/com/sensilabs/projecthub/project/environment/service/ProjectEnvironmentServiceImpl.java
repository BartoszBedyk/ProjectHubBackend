package com.sensilabs.projecthub.project.environment.service;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.project.ProjectRepository;
import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.forms.CreateProjectEnvironmentForm;
import com.sensilabs.projecthub.project.environment.forms.UpdateProjectEnvironmentForm;
import com.sensilabs.projecthub.project.environment.repository.ProjectEnvironmentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectEnvironmentServiceImpl implements ProjectEnvironmentService {

    private final ProjectEnvironmentRepository projectEnvironmentRepository;
    private final ProjectRepository projectRepository;

    public ProjectEnvironmentServiceImpl(ProjectEnvironmentRepository projectEnvironmentRepository, ProjectRepository projectRepository) {
        this.projectEnvironmentRepository = projectEnvironmentRepository;
        this.projectRepository = projectRepository;
    }

    private ProjectEnvironment getOrThrow(String id) {
        return projectEnvironmentRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_ENVIRONMENT_NOT_FOUND));
    }

    @Override
    public void create(CreateProjectEnvironmentForm createProjectEnvironmentForm, String createdById) {
        if(projectRepository.findById(createProjectEnvironmentForm.getProjectId()).isPresent()) {
            ProjectEnvironment projectEnvironment = ProjectEnvironment.builder()
                    .id(UUID.randomUUID().toString())
                    .name(createProjectEnvironmentForm.getName())
                    .isEncrypted(createProjectEnvironmentForm.getIsEncrypted())
                    .projectId(createProjectEnvironmentForm.getProjectId())
                    .createdOn(Instant.now())
                    .updatedOn(null)
                    .deletedOn(null)
                    .deletedById(null)
                    .createdById(createdById)
                    .build();
            projectEnvironmentRepository.save(projectEnvironment);
        } else {
            throw new ApplicationException(ErrorCode.PROJECT_NOT_FOUND);
        }
    }

    @Override
    public void createDefaultEnvironments(String projectId) {
        create(new CreateProjectEnvironmentForm("DEV", false, projectId), "SYSTEM");
        create(new CreateProjectEnvironmentForm("PROD", true, projectId), "SYSTEM");
    }

    @Override
    public void update(UpdateProjectEnvironmentForm updateProjectEnvironmentForm) {
        ProjectEnvironment existingEnvironment = getOrThrow(updateProjectEnvironmentForm.getId());
        existingEnvironment.setName(updateProjectEnvironmentForm.getName());
        existingEnvironment.setEncrypted(updateProjectEnvironmentForm.isEncrypted());
        existingEnvironment.setUpdatedOn(Instant.now());
        projectEnvironmentRepository.save(existingEnvironment);
    }

    @Override
    public void delete(String id, String deletedById) {
        ProjectEnvironment existingEnvironment = getOrThrow(id);
        existingEnvironment.setDeletedOn(Instant.now());
        existingEnvironment.setDeletedById(deletedById);
        projectEnvironmentRepository.save(existingEnvironment);
    }

    @Override
    public List<ProjectEnvironment> getAllEnvironments(String projectId) {
        return projectEnvironmentRepository.findAllNotDeleted(projectId);
    }
}
