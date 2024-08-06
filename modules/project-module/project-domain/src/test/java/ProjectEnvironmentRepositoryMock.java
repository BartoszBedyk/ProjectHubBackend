import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.repository.ProjectEnvironmentRepository;

import java.util.*;

public class ProjectEnvironmentRepositoryMock implements ProjectEnvironmentRepository {
    Map<String, ProjectEnvironment> mockDB = new HashMap<>();


    @Override
    public ProjectEnvironment save(ProjectEnvironment projectEnvironment) {
       return mockDB.put(projectEnvironment.getId(), projectEnvironment);

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
    public List<ProjectEnvironment> findAllNotDeleted(String projectId) {
        List<ProjectEnvironment> envs = new ArrayList<>();
        for (ProjectEnvironment env : mockDB.values()) {
            if (env.getProjectId().equals(projectId)) {
                envs.add(env);
            }
        }
        return envs;
    }

    @Override
    public List<ProjectEnvironment> findAllByIds(List<String> ids) {
        List<ProjectEnvironment> envs = new ArrayList<>();
        for (ProjectEnvironment env : mockDB.values()) {
            if (ids.contains(env.getId())) {
                envs.add(env);
            }
        }
        return envs;
    }
}
