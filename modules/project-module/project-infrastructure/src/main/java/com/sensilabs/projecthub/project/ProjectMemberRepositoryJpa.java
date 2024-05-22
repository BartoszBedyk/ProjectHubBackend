package com.sensilabs.projecthub.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepositoryJpa  extends JpaRepository<ProjectMemberEntity, String>, JpaSpecificationExecutor<ProjectMemberEntity> {

    @Query("SELECT pm FROM ProjectMemberEntity pm WHERE pm.userId = :userId AND pm.projectId = :projectId")
    Optional<ProjectMemberEntity> findByUserIdAndProjectId(@Param("userId") String userId, @Param("projectId") String projectId);

    List<ProjectMemberEntity> findAllByProjectId(String projectId);
}
