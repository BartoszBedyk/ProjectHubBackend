package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.activity.ActivityService;
import com.sensilabs.projecthub.commons.*;
import com.sensilabs.projecthub.notification.EmailingService;
import com.sensilabs.projecthub.notification.NotificationService;
import com.sensilabs.projecthub.notification.forms.ResetPasswordMailFrom;
import com.sensilabs.projecthub.notification.model.Notification;
import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentService;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.service.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private final ProjectRepository projectRepository;
    private final ProjectEnvironmentService projectEnvironmentService;
    private final ProjectMemberService projectMemberService;
    private final UserManagementService userManagementService;
    private final ActivityService activityService;
    private final NotificationService notificationService;
    private final EmailingService emailingService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectEnvironmentService projectEnvironmentService, ProjectMemberService projectMemberService, UserManagementService userManagementService, ActivityService activityService, NotificationService notificationService, EmailingService emailingService) {
        this.projectRepository = projectRepository;
        this.projectEnvironmentService = projectEnvironmentService;
        this.projectMemberService = projectMemberService;
        this.userManagementService = userManagementService;
        this.activityService = activityService;
        this.notificationService = notificationService;
        this.emailingService = emailingService;
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
        String link = "http://localhost:3000/project/" + savedProject.getId();
        Notification notification = notificationService.save(new ResetPasswordMailFrom(user.getFirstName(), user.getLastName(), user.getEmail(), link), "SYSTEM");
        emailingService.send(notification);
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

        activityService.save(new com.sensilabs.projecthub.activity.forms.UpdateProjectForm(updateProjectForm.getId()), userId);

            existingProject.setName(updateProjectForm.getName());
            existingProject.setDescription(updateProjectForm.getDescription());
            existingProject.setTechnologies(new ArrayList<>());
            existingProject.setTechnologies(updateProjectForm.getTechnologyList());
            return projectRepository.save(existingProject);
    }

    @Override
    public SearchResponse<Project> search(SearchForm searchForm, String loggedUserId) {
        return projectRepository.search(searchForm);
    }

    @Override
    public Project delete(String id, String deletedById) {
        Project existingProject = getOrThrow(id);

        if(!projectMemberService.getById(deletedById, existingProject.getId()).getRole().equals(Role.OWNER)){
            throw new ApplicationException(ErrorCode.NOT_PROJECT_OWNER);
        }

        existingProject.setDeletedById(deletedById);
        existingProject.setDeletedOn(Instant.now());
        return projectRepository.save(existingProject);
    }
}
