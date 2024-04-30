import com.sensilabs.projecthub.project.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ProjectServiceTest {
    ProjectRepository projectRepository = new ProjectRepositoryMock();
    ProjectService projectService = new ProjectServiceImpl(projectRepository);

    @Test
    void createProjectTest() throws InterruptedException {
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        CreateProjectForm createProjectForm = new CreateProjectForm("testUser", "Project", "Description",
                List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                        new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
        Project project = projectService.save(createProjectForm, createProjectForm.getUserId());
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(project.getCreatedById(), "testUser");
        Assertions.assertEquals(project.getName(), "Project");
        Assertions.assertEquals(project.getDescription(), "Description");
        Assertions.assertTrue(project.getCreatedOn().isAfter(beforeDate) && project.getCreatedOn().isBefore(afterDate));
        List<Technology> technologyList = project.getTechnologies();

        Technology javaTechnology = technologyList.get(0);
        Assertions.assertEquals("Java", javaTechnology.getName());
        Assertions.assertEquals("JavaDesc", javaTechnology.getDescription());

        Technology springTechnology = technologyList.get(1);
        Assertions.assertEquals("Spring", springTechnology.getName());
        Assertions.assertEquals("SpringDesc", springTechnology.getDescription());
    }

    @Test
    void updateProjectTest() {
        CreateProjectForm createProjectForm = new CreateProjectForm("testUser", "Project", "Description",
                List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                        new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
        Project project = projectService.save(createProjectForm, createProjectForm.getUserId());
        UpdateProjectForm updateProjectForm = new UpdateProjectForm(project.getId(), "Project2", "Description2",
                List.of(new Technology(UUID.randomUUID().toString(), "C++", "C++ Desc"),
                        new Technology(UUID.randomUUID().toString(), "PHP", "PHP Desc")));

        projectService.update(updateProjectForm);

        Assertions.assertEquals(project.getName(), "Project2");
        Assertions.assertEquals(project.getDescription(), "Description2");
        List<Technology> technologyList = project.getTechnologies();

        Technology javaTechnology = technologyList.get(0);
        Assertions.assertEquals("C++", javaTechnology.getName());
        Assertions.assertEquals("C++ Desc", javaTechnology.getDescription());

        Technology springTechnology = technologyList.get(1);
        Assertions.assertEquals("PHP", springTechnology.getName());
        Assertions.assertEquals("PHP Desc", springTechnology.getDescription());
    }

    @Test
    void getTest() throws InterruptedException {
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        CreateProjectForm createProjectForm = new CreateProjectForm("testUser", "Project", "Description",
                List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                        new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
        Project project = projectService.save(createProjectForm, createProjectForm.getUserId());
        Thread.sleep(2);
        Instant afterDate = Instant.now();

        Project projectGet = projectService.getById(project.getId());

        Assertions.assertEquals(projectGet.getCreatedById(), "testUser");
        Assertions.assertEquals(projectGet.getName(), "Project");
        Assertions.assertEquals(projectGet.getDescription(), "Description");
        Assertions.assertTrue(projectGet.getCreatedOn().isAfter(beforeDate) && projectGet.getCreatedOn().isBefore(afterDate));
        List<Technology> technologyList = projectGet.getTechnologies();

        Technology javaTechnology = technologyList.get(0);
        Assertions.assertEquals("Java", javaTechnology.getName());
        Assertions.assertEquals("JavaDesc", javaTechnology.getDescription());

        Technology springTechnology = technologyList.get(1);
        Assertions.assertEquals("Spring", springTechnology.getName());
        Assertions.assertEquals("SpringDesc", springTechnology.getDescription());

    }
}
