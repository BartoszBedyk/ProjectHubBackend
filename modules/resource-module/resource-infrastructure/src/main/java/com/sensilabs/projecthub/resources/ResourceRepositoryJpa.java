package com.sensilabs.projecthub.resources;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepositoryJpa extends JpaRepository<ResourceEntity, String>, JpaSpecificationExecutor<ResourceEntity> {
    @Query("SELECT COUNT(pme) > 0 " +
            "FROM ProjectMemberEntity pme " +
            "JOIN pme.environmentIds env " +
            "WHERE pme.userId = :userId " +
            "AND pme.projectId = :projectId " +
            "AND env = :environmentId")
    Boolean checkAccess(@Param("projectId") String projectId,
                        @Param("environmentId") String environmentId,
                        @Param("userId") String userId);


    @Query("SELECT re " +
            "FROM resource re " +
            "JOIN ProjectMemberEntity pme ON re.projectId = pme.projectId " +
            "JOIN pme.environmentIds env ON re.environmentId = env " +
            "WHERE pme.userId = :userId")
    List<ResourceEntity> findResourcesForUser(@Param("userId") String userId);
}
