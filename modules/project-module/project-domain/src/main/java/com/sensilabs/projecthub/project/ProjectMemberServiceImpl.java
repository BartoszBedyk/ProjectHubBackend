package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService{

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectMember save(CreateProjectMemberForm createProjectMemberForm, String createdById) {
        if(projectRepository.findById(createProjectMemberForm.getProjectId()).isPresent()) {
            ProjectMember projectMember = ProjectMember.builder()
                    .role(createProjectMemberForm.getRole())
                    .firstName(createProjectMemberForm.getFirstName())
                    .lastName(createProjectMemberForm.getLastName())
                    .id(UUID.randomUUID().toString())
                    .createdById(createdById)
                    .createdOn(Instant.now())
                    .projectId(createProjectMemberForm.getProjectId())
                    .build();
            return projectMemberRepository.save(projectMember);
        }
       else
           throw new ApplicationException(ErrorCode.PROJECT_NOT_FOUND);
    }

    @Override
    public ProjectMember update(UpdateProjectMemberForm updateProjectMemberForm) {
        ProjectMember existingMember = getOrThrow(updateProjectMemberForm.getId());
        existingMember.setRole(updateProjectMemberForm.getRole());
        return projectMemberRepository.save(existingMember);
    }

    @Override
    public void remove(String memberId) {
        ProjectMember existingMember = getOrThrow(memberId);
        projectMemberRepository.delete(existingMember);
    }

    @Override
    public SearchResponse<ProjectMember> search(SearchForm searchForm) {
        return projectMemberRepository.search(searchForm);
    }

    @Override
    public ProjectMember getById(String id) {
        return getOrThrow(id);
    }

    private ProjectMember getOrThrow(String id) {
        return projectMemberRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));
    }
}