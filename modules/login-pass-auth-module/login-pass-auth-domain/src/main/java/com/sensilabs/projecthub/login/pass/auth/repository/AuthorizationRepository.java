package com.sensilabs.projecthub.login.pass.auth.repository;

import com.sensilabs.projecthub.login.pass.auth.AuthPassUser;
import com.sensilabs.projecthub.login.pass.auth.ResetPasswordRequest;

import java.util.Optional;

public interface AuthorizationRepository {
    Optional<AuthPassUser> findByEmail(String email);

    Optional<AuthPassUser> findById(String id);

    Optional<ResetPasswordRequest> findByRequestId(String id);

    void saveAuthPassUser(AuthPassUser user);

    void saveResetPasswordRequest(ResetPasswordRequest request);
}
