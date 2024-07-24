import com.sensilabs.projecthub.project.Technology;
import com.sensilabs.projecthub.project.TechnologyRepository;

import java.util.*;

public class TechnologyRepositoryMock implements TechnologyRepository {
    Map<String, Technology> mockDB = new HashMap<>();

    @Override
    public Technology save(Technology technology) {
        mockDB.put(technology.getId(), technology);
        return technology;
    }

    @Override
    public List<Technology> findAll() {
        return new ArrayList<>(mockDB.values());
    }

    @Override
    public Optional<Technology> findById(String id) {
        return Optional.empty();
    }
}
