import com.sensilabs.projecthub.project.*;
import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.forms.CreateProjectEnvironmentForm;
import com.sensilabs.projecthub.project.environment.forms.UpdateProjectEnvironmentForm;
import com.sensilabs.projecthub.project.environment.repository.ProjectEnvironmentRepository;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentService;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentServiceImpl;
import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectEnvironmentServiceTest {

    ProjectRepository projectRepository = new ProjectRepositoryMock();
    ProjectEnvironmentRepository projectEnvironmentRepository = new ProjectEnvironmentRepositoryMock();
    ProjectEnvironmentService service = new ProjectEnvironmentServiceImpl(projectEnvironmentRepository, projectRepository);
    ProjectService projectService = new ProjectServiceImpl(projectRepository, service);
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();


    CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
            List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                    new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
    Project project = projectService.save(createProjectForm, "testUserId");



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
        List<ProjectEnvironment> envs = projectEnvironmentRepository.findAllNotDeletedEnvs(project.getId());

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
            service.update(updateProjectEnvironmentForm);
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
