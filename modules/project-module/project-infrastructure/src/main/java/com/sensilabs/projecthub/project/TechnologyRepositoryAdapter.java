package com.sensilabs.projecthub.project;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TechnologyRepositoryAdapter implements TechnologyRepository{
    private final TechnologyRepositoryJpa technologyRepositoryJpa;

    public TechnologyRepositoryAdapter(TechnologyRepositoryJpa technologyRepositoryJpa) {
        this.technologyRepositoryJpa = technologyRepositoryJpa;
    }

    @Override
    public Technology save(Technology technology) {
        TechnologyEntity technologyEntity = TechnologyMapper.toTechnologyEntity(technology);
        technologyRepositoryJpa.save(technologyEntity);
        return TechnologyMapper.toTechnology(technologyEntity);
    }

    @Override
    public List<Technology> findAll() {
        return technologyRepositoryJpa.findAll().stream()
                .map(TechnologyMapper::toTechnology)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Technology> findById(String id) {
        return technologyRepositoryJpa.findById(id).map(TechnologyMapper::toTechnology);
    }
}
