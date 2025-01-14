package com.sensilabs.projecthub.project.environment.service;

import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.forms.CreateProjectEnvironmentForm;
import com.sensilabs.projecthub.project.environment.forms.UpdateProjectEnvironmentForm;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProjectEnvironmentService {
    ProjectEnvironment create(CreateProjectEnvironmentForm createProjectEnvironmentForm, String createdById);
    void createDefaultEnvironments(String projectId);
    ProjectEnvironment update(UpdateProjectEnvironmentForm updateProjectEnvironmentForm,String loggedUser);
    void delete(String id, String deletedById);
    List<ProjectEnvironment> getAllEnvironments(String projectId);

    ProjectEnvironment findById(String id);
}
