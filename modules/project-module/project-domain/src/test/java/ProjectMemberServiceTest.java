import com.sensilabs.projecthub.activity.ActivityRepository;
import com.sensilabs.projecthub.activity.ActivityService;
import com.sensilabs.projecthub.activity.ActivityServiceImpl;
import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.commons.LoggedUser;
import com.sensilabs.projecthub.project.*;
import com.sensilabs.projecthub.project.environment.repository.ProjectEnvironmentRepository;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentService;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentServiceImpl;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import com.sensilabs.projecthub.user.management.service.UserManagementService;
import com.sensilabs.projecthub.user.management.service.UserManagementServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectMemberServiceTest {
    ProjectMemberRepository projectMemberRepository = new ProjectMemberRepositoryMock();
    ProjectRepository projectRepository = new ProjectRepositoryMock();
    ProjectEnvironmentRepository projectEnvironmentRepository = new ProjectEnvironmentRepositoryMock();
    UserManagementRepository userManagementRepository = new UserManagementRepositoryMock();
    LoggedUser loggedUser = new LoggedUserMock();
    ActivityRepository activityRepository = new ActivityRepositoryMock();
    ActivityService activityService = new ActivityServiceImpl(activityRepository);
    UserManagementService userManagementService = new UserManagementServiceImpl(userManagementRepository, activityService);
    ProjectEnvironmentService projectEnvironmentService = new ProjectEnvironmentServiceImpl(projectEnvironmentRepository, projectRepository, activityService);
    ProjectMemberService projectMemberService = new ProjectMemberServiceImpl(projectMemberRepository, projectRepository, projectEnvironmentRepository, activityService);
    ProjectService projectService = new ProjectServiceImpl(projectRepository, projectEnvironmentService, projectMemberService, userManagementService, activityService);

    @Test
    void saveMemberTest() throws InterruptedException {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"), "1");
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of("1", "2", "3"));
        Project project = projectService.save(createProjectForm, user.getId());
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        List<String> envIds = projectMemberRepository.findById(user.getId(), project.getId()).get().getEnvironmentIds();
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.MAINTAINER, project.getId(), user.getId(), envIds);
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, user.getId());
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(projectMember.getCreatedById(), user.getId());
        Assertions.assertEquals(projectMember.getFirstName(), "Piotr");
        Assertions.assertEquals(projectMember.getLastName(), "Nowak");
        assertTrue(projectMember.getCreatedOn().isAfter(beforeDate) && projectMember.getCreatedOn().isBefore(afterDate));
        Assertions.assertEquals(projectMember.getRole(), Role.MAINTAINER);
        Assertions.assertEquals(projectMember.getProjectId(), project.getId());
        Assertions.assertEquals(projectMember.getEnvironmentIds(), envIds);
        System.out.println("ProjectMember: " + projectMember);
    }

    @Test
    void saveMemberTestBadProject() {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"), "1");
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of("1", "2", "3"));
        Project project = projectService.save(createProjectForm, user.getId());
        List<String> envIds = projectMemberRepository.findById(user.getId(), project.getId()).get().getEnvironmentIds();
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.MAINTAINER, UUID.randomUUID().toString(), user.getId(), envIds);
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            projectMemberService.save(createProjectMemberForm, user.getId());
        });

        Assertions.assertEquals(ErrorCode.PROJECT_NOT_FOUND, exception.getCode());
    }

    @Test
    void saveMemberTestNotOwner() {
        User userNotOwner = userManagementService.save(new CreateUserForm("Mariusz", "Szpakowski", "mariuszszpakowski@gmail.com"), "1");
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"), "1");
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of("1", "2", "3"));
        Project project = projectService.save(createProjectForm, user.getId());
        List<String> envIds = projectMemberRepository.findById(user.getId(), project.getId()).get().getEnvironmentIds();
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.MAINTAINER, project.getId(), user.getId(), envIds);
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            projectMemberService.save(createProjectMemberForm, userNotOwner.getId());
        });

        Assertions.assertEquals(ErrorCode.NOT_PROJECT_OWNER, exception.getCode());
    }

    @Test
    void saveMemberTestBadEnvs() {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"), "1");
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of("1", "2", "3"));
        Project project = projectService.save(createProjectForm, user.getId());
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.MAINTAINER, project.getId(), user.getId(), List.of(UUID.randomUUID().toString()));
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            projectMemberService.save(createProjectMemberForm, user.getId());
        });

        Assertions.assertEquals(ErrorCode.PROJECT_ENVIRONMENT_NOT_FOUND, exception.getCode());
    }

    @Test
    void updateMemberTest() {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"), "1");
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of("1", "2", "3"));
        Project project = projectService.save(createProjectForm, user.getId());
        User secondUser = userManagementService.save(new CreateUserForm("Mariusz", "Szpakowski", "mariuszszpakowski@gmail.com"), "2");
        List<String> envIds = projectMemberRepository.findById(user.getId(), project.getId()).get().getEnvironmentIds();
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Mariusz", "Szpakowski", Role.MAINTAINER, project.getId(), secondUser.getId(), envIds);
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, user.getId());
        UpdateProjectMemberForm updateProjectMemberForm = new UpdateProjectMemberForm(projectMember.getUserId(), projectMember.getProjectId(), Role.VISITOR, projectMember.getEnvironmentIds());
        ProjectMember projectMemberUpdated = projectMemberService.update(updateProjectMemberForm, user.getId());
        Assertions.assertEquals(projectMemberUpdated.getRole(), Role.VISITOR);
    }

    @Test
    void updateMemberTestBadProject() {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"), "1");
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of("1", "2", "3"));
        Project project = projectService.save(createProjectForm, user.getId());
        User secondUser = userManagementService.save(new CreateUserForm("Mariusz", "Szpakowski", "mariuszszpakowski@gmail.com"), "2");
        List<String> envIds = projectMemberRepository.findById(user.getId(), project.getId()).get().getEnvironmentIds();
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Mariusz", "Szpakowski", Role.MAINTAINER, project.getId(), secondUser.getId(), envIds);
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, user.getId());
        UpdateProjectMemberForm updateProjectMemberForm = new UpdateProjectMemberForm(projectMember.getUserId(), UUID.randomUUID().toString(), Role.VISITOR, projectMember.getEnvironmentIds());
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            projectMemberService.update(updateProjectMemberForm, user.getId());
        });
        Assertions.assertEquals(ErrorCode.PROJECT_NOT_FOUND, exception.getCode());
    }

    @Test
    void updateMemberTestBadEnvs() {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"), "1");
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of("1", "2", "3"));
        Project project = projectService.save(createProjectForm, user.getId());
        User secondUser = userManagementService.save(new CreateUserForm("Mariusz", "Szpakowski", "mariuszszpakowski@gmail.com"), "2");
        List<String> envIds = projectMemberRepository.findById(user.getId(), project.getId()).get().getEnvironmentIds();
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Mariusz", "Szpakowski", Role.MAINTAINER, project.getId(), secondUser.getId(), envIds);
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, user.getId());
        UpdateProjectMemberForm updateProjectMemberForm = new UpdateProjectMemberForm(projectMember.getUserId(), projectMember.getProjectId(), Role.VISITOR, List.of(UUID.randomUUID().toString()));
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            projectMemberService.update(updateProjectMemberForm, user.getId());
        });
        Assertions.assertEquals(ErrorCode.PROJECT_ENVIRONMENT_NOT_FOUND, exception.getCode());
    }

    @Test
    void updateMemberTestNotOwner() {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"), "1");
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of("1", "2", "3"));
        Project project = projectService.save(createProjectForm, user.getId());
        User secondUser = userManagementService.save(new CreateUserForm("Mariusz", "Szpakowski", "mariuszszpakowski@gmail.com"), "2");
        List<String> envIds = projectMemberRepository.findById(user.getId(), project.getId()).get().getEnvironmentIds();
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Mariusz", "Szpakowski", Role.MAINTAINER, project.getId(), secondUser.getId(), envIds);
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, user.getId());
        UpdateProjectMemberForm updateProjectMemberForm = new UpdateProjectMemberForm(projectMember.getUserId(), projectMember.getProjectId(), Role.VISITOR, projectMember.getEnvironmentIds());
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            projectMemberService.update(updateProjectMemberForm, secondUser.getId());
        });
        Assertions.assertEquals(ErrorCode.NOT_PROJECT_OWNER, exception.getCode());
    }

    @Test
    void deleteMemberTest() {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"), "1");
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of("1", "2", "3"));
        Project project = projectService.save(createProjectForm, user.getId());
        User secondUser = userManagementService.save(new CreateUserForm("Mariusz", "Szpakowski", "mariuszszpakowski@gmail.com"), "2");
        List<String> envIds = projectMemberRepository.findById(user.getId(), project.getId()).get().getEnvironmentIds();
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Mariusz", "Szpakowski", Role.MAINTAINER, project.getId(), secondUser.getId(), envIds);
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, user.getId());
        projectMemberService.remove(projectMember.getUserId(), projectMember.getProjectId(), loggedUser.getUserId());
        assertThrows(RuntimeException.class, () -> projectMemberService.getById(projectMember.getUserId(), projectMember.getProjectId()));
    }
}
