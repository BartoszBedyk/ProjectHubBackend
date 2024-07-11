package com.sensilabs.projecthub.project;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TechnologyServiceImpl implements TechnologyService{
    private final TechnologyRepository technologyRepository;

    public TechnologyServiceImpl(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Override
    public Technology create(CreateTechnologyForm createTechnologyForm) {
       Technology technology = Technology.builder()
               .id(UUID.randomUUID().toString())
               .name(createTechnologyForm.getName())
               .description(createTechnologyForm.getDescription())
               .build();

        return technologyRepository.save(technology);
    }

    @Override
    public List<Technology> findAll() {
        return technologyRepository.findAll();
    }
}
