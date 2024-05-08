package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;

public interface ProjectMemberService {
    ProjectMember save(CreateProjectMemberForm createProjectMemberForm, String createdById);

    ProjectMember update(UpdateProjectMemberForm updateProjectMemberForm);

    void remove(String memberId);

    SearchResponse<ProjectMember> search(SearchForm searchForm);

    ProjectMember getById(String id);
}
