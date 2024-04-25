package com.sensilabs.projecthub.activity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepositoryJpa extends JpaRepository<ActivityEntity, String>, JpaSpecificationExecutor<ActivityEntity> {
}
