import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.repository.ProjectEnvironmentRepository;

import java.util.*;

public class ProjectEnvironmentRepositoryMock implements ProjectEnvironmentRepository {
    Map<String, ProjectEnvironment> mockDB = new HashMap<>();


    @Override
    public void save(ProjectEnvironment projectEnvironment) {
        mockDB.put(projectEnvironment.getId(), projectEnvironment);

    }

    @Override
    public Optional<ProjectEnvironment> findById(String id) {
        for (ProjectEnvironment env : mockDB.values()) {
            if (env.getId().equals(id)) {
                return Optional.of(env);
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(ProjectEnvironment projectEnvironment) {
        mockDB.remove(projectEnvironment.getId());
    }

    @Override
    public List<ProjectEnvironment> findAll(String projectId) {
        List<ProjectEnvironment> envs = new ArrayList<>();
        for (ProjectEnvironment env : mockDB.values()) {
            if (env.getProjectId().equals(projectId)) {
                envs.add(env);
            }
        }
        return envs;
    }
}
