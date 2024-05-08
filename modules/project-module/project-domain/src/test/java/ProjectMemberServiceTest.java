import com.sensilabs.projecthub.project.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProjectMemberServiceTest {
    ProjectMemberRepository projectMemberRepository = new ProjectMemberRepositoryMock();
    ProjectMemberService projectMemberService = new ProjectMemberServiceImpl(projectMemberRepository);

    @Test
    void saveMemberTest() throws InterruptedException {
        Instant beforeDate = Instant.now();
        Thread.sleep(2);
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.OWNER);
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, "testUser");
        Thread.sleep(2);
        Instant afterDate = Instant.now();
        Assertions.assertEquals(projectMember.getCreatedById(), "testUser");
        Assertions.assertEquals(projectMember.getFirstName(), "Piotr");
        Assertions.assertEquals(projectMember.getLastName(), "Nowak");
        Assertions.assertTrue(projectMember.getCreatedOn().isAfter(beforeDate) && projectMember.getCreatedOn().isBefore(afterDate));
        Assertions.assertEquals(projectMember.getRole(), Role.OWNER);
    }

    @Test
    void updateMemberTest(){
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.OWNER);
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, "testUser");

        UpdateProjectMemberForm updateProjectMemberForm = new UpdateProjectMemberForm(projectMember.getId(), Role.MAINTAINER);
        projectMemberService.update(updateProjectMemberForm);
        Assertions.assertEquals(projectMember.getRole(), Role.MAINTAINER);
    }

    @Test
    void deleteMemberTest(){
        CreateProjectMemberForm createProjectMemberForm = new CreateProjectMemberForm("Piotr", "Nowak", Role.OWNER);
        ProjectMember projectMember = projectMemberService.save(createProjectMemberForm, "testUser");
        String memberId = projectMember.getId();
        projectMemberService.remove(memberId);
        assertThrows(RuntimeException.class, () -> projectMemberService.getById(memberId));
    }

}
