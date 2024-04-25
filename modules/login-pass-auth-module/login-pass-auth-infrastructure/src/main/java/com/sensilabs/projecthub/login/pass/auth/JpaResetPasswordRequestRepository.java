package com.sensilabs.projecthub.login.pass.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaResetPasswordRequestRepository extends JpaRepository<ResetPasswordRequestEntity, String> {

}
