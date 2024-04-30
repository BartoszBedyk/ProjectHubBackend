package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private Project getOrThrow(String id) {
        return projectRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
    }

    @Override
    public Project save(CreateProjectForm createProjectForm, String id) {
        List<Technology> technologies = createProjectForm.getTechnologyList().stream()
                .map(tech -> new Technology(UUID.randomUUID().toString(), tech.getName(), tech.getDescription()))
                .collect(Collectors.toList());

        Project project = Project.builder()
                .id(UUID.randomUUID().toString())
                .name(createProjectForm.getName())
                .description(createProjectForm.getDescription())
                .createdOn(Instant.now())
                .createdById(id)
                .technologies(technologies)
                .build();
        return projectRepository.save(project);
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
        existingProject.setTechnologies(technologies);
        return projectRepository.save(existingProject);
    }

    @Override
    public SearchResponse<Project> search(SearchForm searchForm) {
        return projectRepository.search(searchForm);
    }
}
