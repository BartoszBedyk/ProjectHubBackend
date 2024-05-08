package com.sensilabs.projecthub.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectMemberRepositoryJpa  extends JpaRepository<ProjectMemberEntity, String>, JpaSpecificationExecutor<ProjectMemberEntity> {
}
