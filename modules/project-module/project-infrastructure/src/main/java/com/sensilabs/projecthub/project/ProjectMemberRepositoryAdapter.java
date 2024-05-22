package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.utils.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<ProjectMember> findAllByProjectId(String projectId) {
        return projectMemberRepositoryJpa.findAllByProjectId(projectId).stream().map(ProjectMemberMapper::toProjectMember).toList();
    }


}
