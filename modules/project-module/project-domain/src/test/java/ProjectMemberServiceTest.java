import com.sensilabs.projecthub.project.*;
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
    ProjectService projectService = new ProjectServiceImpl(projectRepository);
    ProjectMemberService projectMemberService = new ProjectMemberServiceImpl(projectMemberRepository);

    @Test
    void saveMemberTest() throws InterruptedException {
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                        new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
        Project project = projectService.save(createProjectForm, "testUser");
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.OWNER, project.getId());
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, "testUser");
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(projectMember.getCreatedById(), "testUser");
        Assertions.assertEquals(projectMember.getFirstName(), "Piotr");
        Assertions.assertEquals(projectMember.getLastName(), "Nowak");
        assertTrue(projectMember.getCreatedOn().isAfter(beforeDate) && projectMember.getCreatedOn().isBefore(afterDate));
        Assertions.assertEquals(projectMember.getRole(), Role.OWNER);
        Assertions.assertEquals(projectMember.getProjectId(), project.getId());
    }

    @Test
    void updateMemberTest(){
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                        new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
        Project project = projectService.save(createProjectForm, "testUser");
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.OWNER, project.getId());
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, "testUser");

        UpdateProjectMemberForm updateProjectMemberForm = new UpdateProjectMemberForm(projectMember.getId(), Role.MAINTAINER);
        projectMemberService.update(updateProjectMemberForm);
        Assertions.assertEquals(projectMember.getRole(), Role.MAINTAINER);
    }

    @Test
    void deleteMemberTest(){
        CreateProjectForm createProjectForm = new CreateProjectForm("Project", "Description",
                List.of(new Technology(UUID.randomUUID().toString(), "Java", "JavaDesc"),
                        new Technology(UUID.randomUUID().toString(), "Spring", "SpringDesc")));
        Project project = projectService.save(createProjectForm, "testUser");
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.OWNER,project.getId());
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, "testUser");
        String memberId = projectMember.getId();
        projectMemberService.remove(memberId);
        assertThrows(RuntimeException.class, () -> projectMemberService.getById(memberId));
    }

}
