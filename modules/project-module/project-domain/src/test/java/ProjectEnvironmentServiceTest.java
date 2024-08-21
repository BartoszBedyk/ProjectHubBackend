import com.sensilabs.projecthub.activity.ActivityRepository;
import com.sensilabs.projecthub.activity.ActivityService;
import com.sensilabs.projecthub.activity.ActivityServiceImpl;
import com.sensilabs.projecthub.commons.LoggedUser;
import com.sensilabs.projecthub.notification.EmailingService;
import com.sensilabs.projecthub.notification.NotificationService;
import com.sensilabs.projecthub.project.*;
import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.forms.CreateProjectEnvironmentForm;
import com.sensilabs.projecthub.project.environment.forms.UpdateProjectEnvironmentForm;
import com.sensilabs.projecthub.project.environment.repository.ProjectEnvironmentRepository;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentService;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentServiceImpl;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import com.sensilabs.projecthub.user.management.service.UserManagementService;
import com.sensilabs.projecthub.user.management.service.UserManagementServiceImpl;
import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectEnvironmentServiceTest {

    ProjectRepository projectRepository = new ProjectRepositoryMock();
    ProjectEnvironmentRepository projectEnvironmentRepository = new ProjectEnvironmentRepositoryMock();
    ActivityRepository activityRepository = new ActivityRepositoryMock();
    ActivityService activityService = new ActivityServiceImpl(activityRepository);
    ProjectEnvironmentService service = new ProjectEnvironmentServiceImpl(projectEnvironmentRepository, projectRepository, activityService);
    ProjectMemberRepository projectMemberRepository = new ProjectMemberRepositoryMock();
    UserManagementRepository userManagementRepository = new UserManagementRepositoryMock();
    LoggedUser loggedUser = new LoggedUserMock();
    UserManagementService userManagementService = new UserManagementServiceImpl(userManagementRepository, activityService);
    ProjectMemberService projectMemberService = new ProjectMemberServiceImpl(projectMemberRepository, projectRepository, projectEnvironmentRepository, activityService);
    NotificationService notificationService;
    EmailingService emailingService;
    ProjectService projectService = new ProjectServiceImpl(projectRepository, service, projectMemberService, userManagementService, activityService, notificationService, emailingService);
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    User user = userManagementService.save(new CreateUserForm("Kamil", "Smolarek","smolarekkamil123@gmail.com"), "1");
    CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
            List.of("1", "2", "3"));
    Project project = projectService.save(createProjectForm, user.getId());

    private <T> void genericViolationSet(T form) {
        Set<ConstraintViolation<T>> violations = validator.validate(form);

        assertThrows(ConstraintViolationException.class, () -> {
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException("Validation failed", violations);
            }
        });
    }

    @Test
    void createDefaultEnvironmentsTest() {
        List<ProjectEnvironment> envs = projectEnvironmentRepository.findAllNotDeleted(project.getId());

        assertEquals(2, envs.size());

        envs.sort(Comparator.comparing(ProjectEnvironment::getName));
        ProjectEnvironment devEnv = envs.get(0);
        ProjectEnvironment prodEnv = envs.get(1);

        assertEquals(devEnv.getName(), "DEV");
        assertFalse(devEnv.isEncrypted());
        assertEquals(devEnv.getProjectId(), project.getId());
        assertEquals(devEnv.getCreatedById(), "SYSTEM");

        assertEquals(prodEnv.getName(), "PROD");
        assertTrue(prodEnv.isEncrypted());
        assertEquals(prodEnv.getProjectId(), project.getId());
        assertEquals(prodEnv.getCreatedById(), "SYSTEM");
    }

    @Test
    void createEnvironmentTest() throws InterruptedException {
        Instant beforeCreate = Instant.now();
        Thread.sleep(2);
        CreateProjectEnvironmentForm createProjectEnvironmentForm = new CreateProjectEnvironmentForm("TestEnv", false, project.getId());
        service.create(createProjectEnvironmentForm, "testId");
        Optional<ProjectEnvironment> env = projectEnvironmentRepository.findById("testId");
        Thread.sleep(2);
        Instant afterCreate = Instant.now();
        if (env.isPresent()) {
            assertEquals(env.get().getName(), "TestEnv");
            assertFalse(env.get().isEncrypted());
            assertEquals(env.get().getProjectId(), project.getId());
            assertTrue(env.get().getCreatedOn().isAfter(beforeCreate) && env.get().getCreatedOn().isBefore(afterCreate));
            assertEquals(env.get().getCreatedById(), "testUserId");
        }
    }



    @Test
    void updateEnvironmentTest() {
        CreateProjectEnvironmentForm createProjectEnvironmentForm = new CreateProjectEnvironmentForm("TestEnv", false, project.getId());
        service.create(createProjectEnvironmentForm, "testId");
        Optional<ProjectEnvironment> env = projectEnvironmentRepository.findById("testId");
        if(env.isPresent()) {
            UpdateProjectEnvironmentForm updateProjectEnvironmentForm = new UpdateProjectEnvironmentForm(env.get().getId(), "NewTestEnv", true);
            service.update(updateProjectEnvironmentForm, loggedUser.getUserId());
            Optional<ProjectEnvironment> envUpdated = projectEnvironmentRepository.findById(env.get().getId());
            if (envUpdated.isPresent()) {
                assertEquals(envUpdated.get().getName(), "NewTestEnv");
                assertTrue(envUpdated.get().isEncrypted());
            }
        }
    }

    @Test
    void deleteEnvironmentTest() {
        CreateProjectEnvironmentForm createProjectEnvironmentForm = new CreateProjectEnvironmentForm("TestEnv", false, project.getId());
        service.create(createProjectEnvironmentForm, "testId");
        Optional<ProjectEnvironment> env = projectEnvironmentRepository.findById("testId");
        if (env.isPresent()) {
            service.delete(env.get().getId(), "testDeletedById");
            assertNotNull(env.get().getDeletedOn());
            assertNotNull(env.get().getDeletedById());
        }
    }

    @Test
    void findAllEnvironmentTest() {
        CreateProjectEnvironmentForm createProjectEnvironmentForm = new CreateProjectEnvironmentForm("TestEnv", false, project.getId());
        service.create(createProjectEnvironmentForm, "testId");
        CreateProjectEnvironmentForm createProjectEnvironmentForm2 = new CreateProjectEnvironmentForm("TestEnv2", true, project.getId());
        service.create(createProjectEnvironmentForm2, "testId2");
        List<ProjectEnvironment> envs = service.getAllEnvironments(project.getId());
        assertFalse(envs.isEmpty());
    }

    @Test
    void validateCreateProjectEnvironmentForm() {
        CreateProjectEnvironmentForm form = new CreateProjectEnvironmentForm("TestEnv", false, null);

        genericViolationSet(form);
    }

    @Test
    void validateUpdateProjectEnvironmentForm() {
        UpdateProjectEnvironmentForm form = new UpdateProjectEnvironmentForm("", "NewTestEnv", true);

        genericViolationSet(form);
    }
}
