package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.*;
import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentService;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.service.UserManagementService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectEnvironmentService projectEnvironmentService;
    private final ProjectMemberService projectMemberService;
    private final UserManagementService userManagementService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectEnvironmentService projectEnvironmentService, ProjectMemberService projectMemberService, UserManagementService userManagementService) {
        this.projectRepository = projectRepository;
        this.projectEnvironmentService = projectEnvironmentService;
        this.projectMemberService = projectMemberService;
        this.userManagementService = userManagementService;
    }

    private Project getOrThrow(String id) {
        return projectRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
    }

    @Override
    public Project save(CreateProjectForm createProjectForm, String createdById) {
        Project project = Project.builder()
                .id(UUID.randomUUID().toString())
                .name(createProjectForm.getName())
                .description(createProjectForm.getDescription())
                .createdOn(Instant.now())
                .createdById(createdById)
                .technologies(createProjectForm.getTechnologyList())
                .deletedById(null)
                .deletedOn(null)
                .build();

        Project savedProject = projectRepository.save(project);
        projectEnvironmentService.createDefaultEnvironments(savedProject.getId());
        List<String> defaultEnvironmentIds = projectEnvironmentService.getAllEnvironments(savedProject.getId())
                .stream()
                .map(ProjectEnvironment::getId)
                .collect(Collectors.toList());
        User user = userManagementService.get(createdById);
        projectMemberService.save(new CreateProjectMemberForm(user.getFirstName(),user.getLastName(),Role.OWNER,savedProject.getId(), createdById, defaultEnvironmentIds),createdById);
        return savedProject;
    }

    @Override
    public Project getById(String id) {
        return getOrThrow(id);
    }

    @Override
    public Project update(UpdateProjectForm updateProjectForm, String userId) {

        Project existingProject = getOrThrow(updateProjectForm.getId());

        if(!projectMemberService.getById(userId, existingProject.getId()).getRole().equals(Role.OWNER)){
            throw new ApplicationException(ErrorCode.NOT_PROJECT_OWNER);
        }
            existingProject.setName(updateProjectForm.getName());
            existingProject.setDescription(updateProjectForm.getDescription());
            existingProject.setTechnologies(new ArrayList<>());
            existingProject.setTechnologies(updateProjectForm.getTechnologyList());
            return projectRepository.save(existingProject);
    }

    @Override
    public SearchResponse<Project> search(SearchForm searchForm, String loggedUserId) {
        searchForm.getCriteria().add(new SearchFormCriteria("members.userId",loggedUserId,CriteriaOperator.EQUALS));
        searchForm.getCriteria().add(new SearchFormCriteria("deletedOn",null,CriteriaOperator.EQUALS));
        searchForm.getCriteria().add(new SearchFormCriteria("deletedById",null,CriteriaOperator.EQUALS));
        return projectRepository.search(searchForm);
    }

    @Override
    public Project delete(String id, String deletedById) {
        Project existingProject = getOrThrow(id);
        existingProject.setDeletedById(deletedById);
        existingProject.setDeletedOn(Instant.now());
        return projectRepository.save(existingProject);
    }
}
