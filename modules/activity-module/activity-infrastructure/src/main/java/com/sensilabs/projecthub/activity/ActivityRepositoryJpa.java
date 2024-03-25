package com.sensilabs.projecthub.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepositoryJpa extends JpaRepository<ActivityEntity, String> {
}
