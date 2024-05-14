package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.*;
import com.sensilabs.projecthub.utils.SearchSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectMemberRepositoryAdapter implements ProjectMemberRepository{
    private final ProjectMemberRepositoryJpa projectMemberRepositoryJpa;
    private final ProjectRepositoryJpa projectRepositoryJpa;

    public ProjectMemberRepositoryAdapter(ProjectMemberRepositoryJpa projectMemberRepositoryJpa, ProjectRepositoryJpa projectRepositoryJpa) {
        this.projectMemberRepositoryJpa = projectMemberRepositoryJpa;
        this.projectRepositoryJpa = projectRepositoryJpa;
    }

    @Override
    public ProjectMember save(ProjectMember projectMember) {
        ProjectEntity projectEntity = projectRepositoryJpa.findById(projectMember.getProjectId()).orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
        ProjectMemberEntity projectMemberEntity = ProjectMemberMapper.toProjectMemberEntity(projectMember, projectEntity);
        projectMemberRepositoryJpa.save(projectMemberEntity);
        return ProjectMemberMapper.toProjectMember(projectMemberEntity);
    }

    @Override
    public SearchResponse<ProjectMember> search(SearchForm searchForm) {
        Specification<ProjectMemberEntity> specification = SearchSpecification.buildSpecification(searchForm.getCriteria());
        Page<ProjectMemberEntity> projectMemberPage = projectMemberRepositoryJpa.findAll(specification, SearchSpecification.getPageRequest(searchForm));
        return SearchResponse.<ProjectMember>builder()
                .items(projectMemberPage.getContent().stream()
                        .map(ProjectMemberMapper::toProjectMember)
                        .collect(Collectors.toList()))
                .total(projectMemberPage.getTotalElements())
                .build();
    }

    @Override
    public Optional<ProjectMember> findById(String id) {
        return projectMemberRepositoryJpa.findById(id).map(ProjectMemberMapper::toProjectMember);
    }

    @Override
    public void delete(ProjectMember projectMember) {
        ProjectEntity projectEntity = projectRepositoryJpa.findById(projectMember.getProjectId()).orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
        ProjectMemberEntity projectMemberEntity = ProjectMemberMapper.toProjectMemberEntity(projectMember, projectEntity);
        projectMemberRepositoryJpa.delete(projectMemberEntity);
    }
}
