import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.commons.LoggedUser;
import com.sensilabs.projecthub.project.*;
import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
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

public class ProjectServiceTest {
    ProjectRepository projectRepository = new ProjectRepositoryMock();
    ProjectEnvironmentRepository projectEnvironmentRepository = new ProjectEnvironmentRepositoryMock();
    ProjectMemberRepository projectMemberRepository = new ProjectMemberRepositoryMock();
    LoggedUser loggedUser = new LoggedUserMock();
    UserManagementRepository userManagementRepository = new UserManagementRepositoryMock();
    ProjectEnvironmentService projectEnvironmentService = new ProjectEnvironmentServiceImpl(projectEnvironmentRepository, projectRepository);
    ProjectMemberService projectMemberService = new ProjectMemberServiceImpl(projectMemberRepository, projectRepository, projectEnvironmentRepository);
    UserManagementService userManagementService = new UserManagementServiceImpl(userManagementRepository, loggedUser);
    ProjectService projectService = new ProjectServiceImpl(projectRepository, projectEnvironmentService, projectMemberService, userManagementService);

    @Test
    void createProjectTest() throws InterruptedException {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"));
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                        new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
        Project project = projectService.save(createProjectForm, user.getId());
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(user.getId(), project.getCreatedById());
        Assertions.assertEquals("Project", project.getName());
        Assertions.assertEquals("Description", project.getDescription());
        Assertions.assertTrue(project.getCreatedOn().isAfter(beforeDate) && project.getCreatedOn().isBefore(afterDate));
        List<Technology> technologyList = project.getTechnologies();

        Technology javaTechnology = technologyList.get(0);
        Assertions.assertEquals("Java", javaTechnology.getName());
        Assertions.assertEquals("JavaDesc", javaTechnology.getDescription());

        Technology springTechnology = technologyList.get(1);
        Assertions.assertEquals("Spring", springTechnology.getName());
        Assertions.assertEquals("SpringDesc", springTechnology.getDescription());

        ProjectMember projectMember = projectMemberService.getById(user.getId(), project.getId());
        Assertions.assertEquals(user.getId(), projectMember.getUserId());
        Assertions.assertEquals(Role.OWNER, projectMember.getRole());

        List<ProjectEnvironment> environments = projectEnvironmentService.getAllEnvironments(project.getId());
        Assertions.assertFalse(environments.isEmpty());
    }

    @Test
    void updateProjectTest() {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"));
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                        new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
        Project project = projectService.save(createProjectForm, user.getId());
        UpdateProjectForm updateProjectForm = new UpdateProjectForm(project.getId(), "Project2", "Description2",
                List.of(new Technology(UUID.randomUUID().toString(), "C++", "C++ Desc"),
                        new Technology(UUID.randomUUID().toString(), "PHP", "PHP Desc")));

        projectService.update(updateProjectForm, user.getId());

        Assertions.assertEquals("Project2", project.getName());
        Assertions.assertEquals("Description2", project.getDescription());
        List<Technology> technologyList = project.getTechnologies();

        Technology javaTechnology = technologyList.get(0);
        Assertions.assertEquals("C++", javaTechnology.getName());
        Assertions.assertEquals("C++ Desc", javaTechnology.getDescription());

        Technology springTechnology = technologyList.get(1);
        Assertions.assertEquals("PHP", springTechnology.getName());
        Assertions.assertEquals("PHP Desc", springTechnology.getDescription());
    }

    @Test
    void updateProjectNotOwnerTest() {
        User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek", "smolarekkamil123@gmail.com"));
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                        new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
        Project project = projectService.save(createProjectForm, user.getId());

        UpdateProjectForm updateProjectForm = new UpdateProjectForm(project.getId(), "Project2", "Description2",
                List.of(new Technology(UUID.randomUUID().toString(), "C++", "C++ Desc"),
                        new Technology(UUID.randomUUID().toString(), "PHP", "PHP Desc")));
        User userNotOwner = userManagementService.save(new CreateUserForm("Mariusz", "Szpakowski", "mariuszszpakowski@gmail.com"));
        List<String> envIds = projectMemberRepository.findById(user.getId(), project.getId()).get().getEnvironmentIds();
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Mariusz", "Szpakowski", Role.MAINTAINER, project.getId(), userNotOwner.getId(), envIds);
        projectMemberService.save(createProjectMemberForm, user.getId());
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            projectService.update(updateProjectForm, userNotOwner.getId());
        });

        Assertions.assertEquals(ErrorCode.NOT_PROJECT_OWNER, exception.getCode());
    }
}
