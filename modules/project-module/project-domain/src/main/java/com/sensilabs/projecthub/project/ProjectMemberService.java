package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProjectMemberService {
    ProjectMember save(@Valid CreateProjectMemberForm createProjectMemberForm, String createdById);

    ProjectMember update(@Valid UpdateProjectMemberForm updateProjectMemberForm);

    void remove(String userId, String projectId);

    List<ProjectMember> findAllByProjectId(String projectId);

    ProjectMember getById(String userId, String projectId);
}
