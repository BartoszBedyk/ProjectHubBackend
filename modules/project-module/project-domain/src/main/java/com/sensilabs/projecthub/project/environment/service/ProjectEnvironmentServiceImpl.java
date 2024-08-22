package com.sensilabs.projecthub.project.environment.service;

import com.sensilabs.projecthub.activity.ActivityService;
import com.sensilabs.projecthub.activity.forms.DeleteProjectEnvironmentForm;
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
    private final ActivityService activityService;

    public ProjectEnvironmentServiceImpl(ProjectEnvironmentRepository projectEnvironmentRepository, ProjectRepository projectRepository, ActivityService activityService) {
        this.projectEnvironmentRepository = projectEnvironmentRepository;
        this.projectRepository = projectRepository;
        this.activityService = activityService;
    }

    private ProjectEnvironment getOrThrow(String id) {
        return projectEnvironmentRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_ENVIRONMENT_NOT_FOUND));
    }

    @Override
    public ProjectEnvironment create(CreateProjectEnvironmentForm createProjectEnvironmentForm, String createdById) {
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

            activityService.save(new com.sensilabs.projecthub.activity.forms.CreateProjectEnvironmentForm(projectEnvironment.getProjectId(), projectEnvironment.getId()), createdById);

            return projectEnvironmentRepository.save(projectEnvironment);
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
    public ProjectEnvironment update(UpdateProjectEnvironmentForm updateProjectEnvironmentForm, String loggedUser) {
        ProjectEnvironment existingEnvironment = getOrThrow(updateProjectEnvironmentForm.getId());
        existingEnvironment.setName(updateProjectEnvironmentForm.getName());
        existingEnvironment.setEncrypted(updateProjectEnvironmentForm.isEncrypted());
        existingEnvironment.setUpdatedOn(Instant.now());
        activityService.save(new com.sensilabs.projecthub.activity.forms.UpdateProjectEnvironmentForm(existingEnvironment.getProjectId(), updateProjectEnvironmentForm.getId()), loggedUser);
        return projectEnvironmentRepository.save(existingEnvironment);
    }

    @Override
    public void delete(String id, String deletedById) {
        ProjectEnvironment existingEnvironment = getOrThrow(id);
        existingEnvironment.setDeletedOn(Instant.now());
        existingEnvironment.setDeletedById(deletedById);
        activityService.save(new DeleteProjectEnvironmentForm(existingEnvironment.getProjectId(), id), deletedById);
        projectEnvironmentRepository.save(existingEnvironment);
    }

    @Override
    public List<ProjectEnvironment> getAllEnvironments(String projectId) {
        return projectEnvironmentRepository.findAllNotDeleted(projectId);
    }

    @Override
    public ProjectEnvironment findById(String id) {
        return getOrThrow(id);
    }
}
