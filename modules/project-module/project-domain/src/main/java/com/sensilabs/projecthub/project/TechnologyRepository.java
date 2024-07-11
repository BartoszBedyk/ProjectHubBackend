package com.sensilabs.projecthub.project;

import java.util.List;

public interface TechnologyRepository {
    Technology save(Technology technology);

    List<Technology> findAll();
}
