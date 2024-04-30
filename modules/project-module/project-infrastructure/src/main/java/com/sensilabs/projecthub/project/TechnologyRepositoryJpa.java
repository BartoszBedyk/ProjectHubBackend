package com.sensilabs.projecthub.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepositoryJpa extends JpaRepository<TechnologyEntity, String> {
}
