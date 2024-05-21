package com.sensilabs.projecthub.project;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProjectMemberService {
    ProjectMember save(@Valid CreateProjectMemberForm createProjectMemberForm, String createdById);

    ProjectMember update(@Valid UpdateProjectMemberForm updateProjectMemberForm);

    void remove(String userId, String projectId);

    List<ProjectDTO> getProjects(String userId);

    ProjectMember getById(String userId, String projectId);
}
