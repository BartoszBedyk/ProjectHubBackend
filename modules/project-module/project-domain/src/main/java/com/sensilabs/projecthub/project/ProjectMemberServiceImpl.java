package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectMember save(CreateProjectMemberForm createProjectMemberForm, String createdById) {
        if (projectRepository.findById(createProjectMemberForm.getProjectId()).isPresent()) {


            ProjectMember projectMember = ProjectMember.builder()
                    .role(createProjectMemberForm.getRole())
                    .firstName(createProjectMemberForm.getFirstName())
                    .lastName(createProjectMemberForm.getLastName())
                    .createdById(createdById)
                    .createdOn(Instant.now())
                    .projectId(createProjectMemberForm.getProjectId())
                    .userId(createProjectMemberForm.getUserId())
                    .environmentIds(createProjectMemberForm.getEnvironmentIds())
                    .build();
            return projectMemberRepository.save(projectMember);
        } else {
            throw new ApplicationException(ErrorCode.PROJECT_NOT_FOUND);
        }
    }

    @Override
    public ProjectMember update(UpdateProjectMemberForm updateProjectMemberForm) {
        ProjectMember existingMember = getOrThrow(updateProjectMemberForm.getUserId(), updateProjectMemberForm.getProjectId());
        existingMember.setRole(updateProjectMemberForm.getRole());
        return projectMemberRepository.save(existingMember);
    }

    @Override
    public void remove(String userId, String projectId) {
        ProjectMember existingMember = getOrThrow(userId, projectId);
        projectMemberRepository.delete(existingMember);
    }

    @Override
    public List<ProjectMember> findAllByProjectId(String projectId) {
        return projectMemberRepository.findAllByProjectId(projectId);
    }

    @Override
    public ProjectMember getById(String userId, String projectId) {
        return getOrThrow(userId, projectId);
    }

    private ProjectMember getOrThrow(String userId, String projectId) {
        return projectMemberRepository.findById(userId, projectId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));
    }
}
