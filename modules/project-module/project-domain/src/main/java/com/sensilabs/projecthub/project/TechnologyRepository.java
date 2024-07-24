package com.sensilabs.projecthub.project;

import java.util.List;
import java.util.Optional;

public interface TechnologyRepository {
    Technology save(Technology technology);

    List<Technology> findAll();

    Optional<Technology> findById(String id);
}
