package com.sensilabs.projecthub.project;

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

    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository, ProjectEnvironmentRepository projectEnvironmentRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.projectEnvironmentRepository = projectEnvironmentRepository;
    }

    @Override
    public ProjectMember save(CreateProjectMemberForm createProjectMemberForm, String createdById) {
        if (projectRepository.findById(createProjectMemberForm.getProjectId()).isPresent()) {
            Project project = projectRepository.findById(createProjectMemberForm.getProjectId()).get();
            Optional<ProjectMember> memberOptional = projectMemberRepository.findById(createdById, createProjectMemberForm.getProjectId());
            boolean isProjectOwner = memberOptional.isPresent() && memberOptional.get().getRole().equals(Role.OWNER);
            if (project.getCreatedById().equals(createdById) || isProjectOwner){
                List<ProjectEnvironment> environments = projectEnvironmentRepository.findAllByIds(createProjectMemberForm.getEnvironmentIds());
                if (environments.size() == createProjectMemberForm.getEnvironmentIds().size()) {
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
                } else {throw new ApplicationException(ErrorCode.PROJECT_ENVIRONMENT_NOT_FOUND);}
            } else {throw new ApplicationException(ErrorCode.NOT_PROJECT_OWNER);}
        } else {throw new ApplicationException(ErrorCode.PROJECT_NOT_FOUND);}
    }

    @Override
    public ProjectMember update(UpdateProjectMemberForm updateProjectMemberForm, String loggedUser) {
        if (projectRepository.findById(updateProjectMemberForm.getProjectId()).isPresent()) {
            ProjectMember member = getOrThrow(loggedUser, updateProjectMemberForm.getProjectId());
            if (member.getRole().equals(Role.OWNER)) {
                List<ProjectEnvironment> environments = projectEnvironmentRepository.findAllByIds(updateProjectMemberForm.getEnvironmentIds());
                if (environments.size() == updateProjectMemberForm.getEnvironmentIds().size()) {
                    ProjectMember existingMember = getOrThrow(updateProjectMemberForm.getUserId(), updateProjectMemberForm.getProjectId());
                    existingMember.setRole(updateProjectMemberForm.getRole());
                    existingMember.getEnvironmentIds().clear();
                    existingMember.setEnvironmentIds(updateProjectMemberForm.getEnvironmentIds());
                    return projectMemberRepository.save(existingMember);
                } else {throw new ApplicationException(ErrorCode.PROJECT_ENVIRONMENT_NOT_FOUND);}
            } else {throw new ApplicationException(ErrorCode.NOT_PROJECT_OWNER);}
        } else {throw new ApplicationException(ErrorCode.PROJECT_NOT_FOUND);}
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
