package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.activity.ActivityService;
import com.sensilabs.projecthub.activity.forms.DeleteProjectMemberForm;
import com.sensilabs.projecthub.commons.*;
import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.repository.ProjectEnvironmentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectEnvironmentRepository projectEnvironmentRepository;
    private final ActivityService activityService;

    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository, ProjectEnvironmentRepository projectEnvironmentRepository, ActivityService activityService) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.projectEnvironmentRepository = projectEnvironmentRepository;
        this.activityService = activityService;
    }

    @Override
    public ProjectMember save(CreateProjectMemberForm createProjectMemberForm, String createdById) {
        Project project = projectRepository.findById(createProjectMemberForm.getProjectId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));

        Optional<ProjectMember> memberOptional = projectMemberRepository.findById(createdById, createProjectMemberForm.getProjectId());

        boolean isProjectOwner = memberOptional.isPresent() && memberOptional.get().getRole().equals(Role.OWNER);
        if (!project.getCreatedById().equals(createdById) && !isProjectOwner) {
            throw new ApplicationException(ErrorCode.NOT_PROJECT_OWNER);
        }

        List<ProjectEnvironment> environments = projectEnvironmentRepository.findAllByIds(createProjectMemberForm.getEnvironmentIds());
        if (environments.size() != createProjectMemberForm.getEnvironmentIds().size()) {
            throw new ApplicationException(ErrorCode.PROJECT_ENVIRONMENT_NOT_FOUND);
        }

        activityService.save(new com.sensilabs.projecthub.activity.forms.CreateProjectMemberForm(project.getId(), createProjectMemberForm.getUserId(), createProjectMemberForm.getRole().toString()), createdById);

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
    }

    @Override
    public ProjectMember update(UpdateProjectMemberForm updateProjectMemberForm, String loggedUser) {
        Project project = projectRepository.findById(updateProjectMemberForm.getProjectId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));

        ProjectMember member = getOrThrow(loggedUser, updateProjectMemberForm.getProjectId());

        if (!member.getRole().equals(Role.OWNER)) {
            throw new ApplicationException(ErrorCode.NOT_PROJECT_OWNER);
        }

        List<ProjectEnvironment> environments = projectEnvironmentRepository.findAllByIds(updateProjectMemberForm.getEnvironmentIds());
        if (environments.size() != updateProjectMemberForm.getEnvironmentIds().size()) {
            throw new ApplicationException(ErrorCode.PROJECT_ENVIRONMENT_NOT_FOUND);
        }

        ProjectMember existingMember = getOrThrow(updateProjectMemberForm.getUserId(), updateProjectMemberForm.getProjectId());

        activityService.save(new com.sensilabs.projecthub.activity.forms.UpdateProjectMemberForm(updateProjectMemberForm.getProjectId(), updateProjectMemberForm.getUserId(), existingMember.getRole().toString(), updateProjectMemberForm.getRole().toString()), loggedUser);

        existingMember.setRole(updateProjectMemberForm.getRole());
        existingMember.getEnvironmentIds().clear();
        existingMember.setEnvironmentIds(updateProjectMemberForm.getEnvironmentIds());

        return projectMemberRepository.save(existingMember);
    }

    @Override
    public void remove(String userId, String projectId, String loggedUser) {
        ProjectMember existingMember = getOrThrow(userId, projectId);

        activityService.save(new DeleteProjectMemberForm(projectId, userId), loggedUser);

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
