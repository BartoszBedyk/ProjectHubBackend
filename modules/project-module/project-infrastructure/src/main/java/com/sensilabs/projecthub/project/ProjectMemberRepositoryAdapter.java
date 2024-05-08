package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.utils.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectMemberRepositoryAdapter implements ProjectMemberRepository{
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
        ProjectMemberEntity projectMemberEntity = ProjectMemberMapper.toProjectMemberEntity(projectMember);
        projectMemberRepositoryJpa.delete(projectMemberEntity);
    }
}
