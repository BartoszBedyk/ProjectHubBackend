package com.sensilabs.projecthub.user.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface JpaUserManagementRepository extends JpaRepository<UserEntity, String> {

    // SELECT FROM user where deletedOn=null and id=?
    Optional<UserEntity> findByIdAndDeletedOnIsNull(String id);
}
