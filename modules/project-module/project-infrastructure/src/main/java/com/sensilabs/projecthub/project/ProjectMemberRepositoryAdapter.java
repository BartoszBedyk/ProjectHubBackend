package com.sensilabs.projecthub.project;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectMemberRepositoryAdapter implements ProjectMemberRepository {
    private final ProjectMemberRepositoryJpa projectMemberRepositoryJpa;

    public ProjectMemberRepositoryAdapter(ProjectMemberRepositoryJpa projectMemberRepositoryJpa) {
        this.projectMemberRepositoryJpa = projectMemberRepositoryJpa;
    }

    @Override
    public ProjectMember save(ProjectMember projectMember) {
        ProjectMemberEntity projectMemberEntity = ProjectMemberMapper.toProjectMemberEntity(projectMember);
        projectMemberRepositoryJpa.save(projectMemberEntity);
        return ProjectMemberMapper.toProjectMember(projectMemberEntity);
    }

    @Override
    public Optional<ProjectMember> findById(String userId, String projectId) {
        return projectMemberRepositoryJpa.findByUserIdAndProjectId(userId, projectId)
                .map(ProjectMemberMapper::toProjectMember);
    }

    @Override
    public void delete(ProjectMember projectMember) {
        ProjectMemberEntity projectMemberEntity = ProjectMemberMapper.toProjectMemberEntity(projectMember);
        projectMemberRepositoryJpa.delete(projectMemberEntity);
    }

    @Override
    public List<Object[]> getProjects(String userId) {
        return projectMemberRepositoryJpa.getProjects(userId);
    }
}
