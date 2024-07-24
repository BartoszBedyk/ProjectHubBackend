package com.sensilabs.projecthub.project;

import java.util.List;

public interface TechnologyService {
    Technology create(CreateTechnologyForm createTechnologyForm);

    List<Technology> findAll();

    Technology get(String id);
}
