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
public class ProjectRepositoryAdapter implements ProjectRepository {

    private final ProjectRepositoryJpa projectRepositoryJpa;

    public ProjectRepositoryAdapter(ProjectRepositoryJpa projectRepositoryJpa) {
        this.projectRepositoryJpa = projectRepositoryJpa;
    }

    @Override
    public Project save(Project project) {
        ProjectEntity projectEntity = ProjectMapper.toProjectEntity(project);
        projectRepositoryJpa.save(projectEntity);
        return ProjectMapper.toProject(projectEntity);
    }

    @Override
    public SearchResponse<Project> search(SearchForm searchForm) {
        Specification<ProjectEntity> specification = SearchSpecification.buildSpecification(searchForm.getCriteria());
        Page<ProjectEntity> projectPage = projectRepositoryJpa.findAll(specification, SearchSpecification.getPageRequest(searchForm));
        return SearchResponse.<Project>builder()
                .items(projectPage.getContent().stream()
                        .map(ProjectMapper::toProject)
                        .collect(Collectors.toList()))
                .total(projectPage.getTotalElements())
                .build();
    }

    @Override
    public Optional<Project> findById(String id) {
        return projectRepositoryJpa.findById(id).map(ProjectMapper::toProject);
    }
}
