package com.sensilabs.projecthub.resources;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResourceRepositoryJpa extends JpaRepository<ResourceEntity, String>, JpaSpecificationExecutor<ResourceEntity> {
}
