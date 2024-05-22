package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.*;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectEnvironmentService projectEnvironmentService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectEnvironmentService projectEnvironmentService) {
        this.projectRepository = projectRepository;
        this.projectEnvironmentService = projectEnvironmentService;
    }

    private Project getOrThrow(String id) {
        return projectRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
    }

    @Override
    public Project save(CreateProjectForm createProjectForm, String createdById) {
        List<Technology> technologies = createProjectForm.getTechnologyList().stream()
                .map(tech -> new Technology(UUID.randomUUID().toString(), tech.getName(), tech.getDescription()))
                .collect(Collectors.toList());

        Project project = Project.builder()
                .id(UUID.randomUUID().toString())
                .name(createProjectForm.getName())
                .description(createProjectForm.getDescription())
                .createdOn(Instant.now())
                .createdById(createdById)
                .technologies(technologies)
                .build();

        Project savedProject = projectRepository.save(project);
        projectEnvironmentService.createDefaultEnvironments(savedProject.getId());
        return savedProject;
    }

    @Override
    public Project getById(String id) {
        return getOrThrow(id);
    }

    @Override
    public Project update(UpdateProjectForm updateProjectForm) {
        List<Technology> technologies = updateProjectForm.getTechnologyList().stream()
                .map(tech -> new Technology(UUID.randomUUID().toString(), tech.getName(), tech.getDescription()))
                .collect(Collectors.toList());

        Project existingProject = getOrThrow(updateProjectForm.getId());
        existingProject.setName(updateProjectForm.getName());
        existingProject.setDescription(updateProjectForm.getDescription());
        existingProject.getTechnologies().clear();
        existingProject.setTechnologies(technologies);
        return projectRepository.save(existingProject);
    }

    @Override
    public SearchResponse<Project> search(SearchForm searchForm, String loggedUserId) {
        searchForm.getCriteria().add(new SearchFormCriteria("members.userId",loggedUserId,CriteriaOperator.EQUALS));
        return projectRepository.search(searchForm);
    }
}
