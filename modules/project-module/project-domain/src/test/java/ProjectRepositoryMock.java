import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.project.Project;
import com.sensilabs.projecthub.project.ProjectRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProjectRepositoryMock implements ProjectRepository {
    Map<String, Project> mockDB = new HashMap<>();

    @Override
    public Project save(Project project) {
        mockDB.put(project.getId(), project);
        return project;
    }

    @Override
    public SearchResponse<Project> search(SearchForm searchForm) {
        return null;
    }

    @Override
    public Optional<Project> findById(String id) {
        return Optional.ofNullable(mockDB.get(id));
    }
}
