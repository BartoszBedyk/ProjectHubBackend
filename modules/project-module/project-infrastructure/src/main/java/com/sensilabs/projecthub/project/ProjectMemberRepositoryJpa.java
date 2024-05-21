package com.sensilabs.projecthub.project;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepositoryJpa  extends JpaRepository<ProjectMemberEntity, String>, JpaSpecificationExecutor<ProjectMemberEntity> {

    @Query(value = "SELECT " +
            "p.id AS projectId, " +
            "p.name AS projectName, " +
            "p.description AS projectDescription, " +
            "p.created_on AS projectCreatedOn, " +
            "p.created_by_id AS projectCreatedById, " +
            "t.id AS technologyId, " +
            "t.name AS technologyName, " +
            "t.description AS technologyDescription " +
            "FROM " +
            "project p " +
            "JOIN " +
            "project_member pm ON p.id = pm.project_id " +
            "LEFT JOIN " +
            "technology t ON p.id = t.project_id " +
            "WHERE " +
            "pm.user_id = :userId " +
            "GROUP BY p.id, t.id", nativeQuery = true)
    List<Object[]> getProjects(@Param("userId") String userId);

    @Query("SELECT pm FROM ProjectMemberEntity pm WHERE pm.userId = :userId AND pm.projectId = :projectId")
    Optional<ProjectMemberEntity> findByUserIdAndProjectId(@Param("userId") String userId, @Param("projectId") String projectId);}
