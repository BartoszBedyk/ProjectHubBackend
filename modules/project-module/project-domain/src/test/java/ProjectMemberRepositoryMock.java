import com.sensilabs.projecthub.project.ProjectMember;
import com.sensilabs.projecthub.project.ProjectMemberRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjectMemberRepositoryMock implements ProjectMemberRepository {
    Map<String, ProjectMember> mockDB = new HashMap<>();

    @Override
    public ProjectMember save(ProjectMember projectMember) {
        mockDB.put(projectMember.getUserId(), projectMember);
        return projectMember;
    }

    @Override
    public Optional<ProjectMember> findById(String userId, String projectId) {
        return Optional.empty();
    }

    @Override
    public void delete(ProjectMember projectMember) {
        mockDB.remove(projectMember.getUserId());
    }

    @Override
    public List<Object[]> getProjects(String userId) {
        return List.of();
    }
}
