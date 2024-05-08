package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService{

    private final ProjectMemberRepository projectMemberRepository;

    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository) {
        this.projectMemberRepository = projectMemberRepository;
    }


    @Override
    public ProjectMember save(CreateProjectMemberForm createProjectMemberForm, String createdById) {
        ProjectMember projectMember = ProjectMember.builder()
                .role(createProjectMemberForm.getRole())
                .firstName(createProjectMemberForm.getFirstName())
                .lastName(createProjectMemberForm.getLastName())
                .id(UUID.randomUUID().toString())
                .createdById(createdById)
                .createdOn(Instant.now())
                .build();
        return projectMemberRepository.save(projectMember);
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
        if (existingMember != null) {
            projectMemberRepository.delete(existingMember);
        } else {
            throw new IllegalArgumentException("Członek projektu o podanym identyfikatorze nie istnieje.");
        }
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