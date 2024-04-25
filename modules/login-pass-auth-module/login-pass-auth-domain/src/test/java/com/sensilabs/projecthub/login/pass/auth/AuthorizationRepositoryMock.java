package com.sensilabs.projecthub.login.pass.auth;

import com.sensilabs.projecthub.login.pass.auth.repository.AuthorizationRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthorizationRepositoryMock implements AuthorizationRepository {
    Map<String, AuthPassUser> AuthPassUserMockDB = new HashMap<>();
    static Map<String, ResetPasswordRequest> RequestMockDB = new HashMap<>();

    @Override
    public Optional<AuthPassUser> findByEmail(String email) {
        return Optional.ofNullable(AuthPassUserMockDB.get(email));
    }

    @Override
    public Optional<AuthPassUser> findById(String id) {
        for (AuthPassUser user : AuthPassUserMockDB.values()) {
            if (user.getId().equals(id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ResetPasswordRequest> findByRequestId(String id) {
        return Optional.ofNullable(RequestMockDB.get(id));
    }

    public static Optional<ResetPasswordRequest> findRequestByUserId(String id) {
        for (ResetPasswordRequest request : RequestMockDB.values()) {
            if (request.getUserId().equals(id)) {
                return Optional.of(request);
            }
        }
        return Optional.empty();
    }

    @Override
    public void saveAuthPassUser(AuthPassUser user) {
        AuthPassUserMockDB.put(user.getEmail(), user);
    }

    @Override
    public void saveResetPasswordRequest(ResetPasswordRequest request) {
        RequestMockDB.put(request.getId(), request);
    }
}
