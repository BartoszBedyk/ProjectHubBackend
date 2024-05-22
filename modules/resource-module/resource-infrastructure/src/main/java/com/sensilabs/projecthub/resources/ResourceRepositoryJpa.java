package com.sensilabs.projecthub.resources;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ResourceRepositoryJpa extends JpaRepository<ResourceEntity, String>, JpaSpecificationExecutor<ResourceEntity> {
}
