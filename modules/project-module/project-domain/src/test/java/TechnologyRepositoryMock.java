import com.sensilabs.projecthub.project.Technology;
import com.sensilabs.projecthub.project.TechnologyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
