import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.project.ProjectMember;
import com.sensilabs.projecthub.project.ProjectMemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProjectMemberRepositoryMock implements ProjectMemberRepository {
    Map<String, ProjectMember> mockDB = new HashMap<>();

    @Override
    public ProjectMember save(ProjectMember projectMember) {
        mockDB.put(projectMember.getId(), projectMember);
        return projectMember;
    }

    @Override
    public SearchResponse<ProjectMember> search(SearchForm searchForm) {
        return null;
    }

    @Override
    public Optional<ProjectMember> findById(String id) {
        return Optional.ofNullable(mockDB.get(id));
    }

    @Override
    public void delete(ProjectMember projectMember) {
        mockDB.remove(projectMember.getId());
    }
}
