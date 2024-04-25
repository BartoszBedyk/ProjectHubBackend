package com.sensilabs.projecthub.login.pass.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaAuthorizationRepository extends JpaRepository<AuthPassUserEntity, String> {

    Optional<AuthPassUserEntity> findByEmail(String email);
}
