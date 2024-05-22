import com.sensilabs.projecthub.project.ProjectMember;
import com.sensilabs.projecthub.project.ProjectMemberRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectMemberRepositoryMock implements ProjectMemberRepository {
    Map<String, ProjectMember> mockDB = new HashMap<>();

    @Override
    public ProjectMember save(ProjectMember projectMember) {
        String key = projectMember.getUserId() + "-" + projectMember.getProjectId();
        mockDB.put(key, projectMember);
        return projectMember;
    }

    @Override
    public Optional<ProjectMember> findById(String userId, String projectId) {
        String key = userId + "-" + projectId;
        return Optional.ofNullable(mockDB.get(key));
    }

    @Override
    public void delete(ProjectMember projectMember) {
        String key = projectMember.getUserId() + "-" + projectMember.getProjectId();
        mockDB.remove(key);
    }

    @Override
    public List<ProjectMember> findAllByProjectId(String projectId) {
        return mockDB.values().stream()
                .filter(member -> projectId.equals(member.getProjectId()))
                .collect(Collectors.toList());
    }
}
