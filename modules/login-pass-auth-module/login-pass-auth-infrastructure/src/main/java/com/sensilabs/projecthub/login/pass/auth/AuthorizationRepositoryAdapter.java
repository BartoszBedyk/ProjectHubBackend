package com.sensilabs.projecthub.login.pass.auth;

import com.sensilabs.projecthub.login.pass.auth.repository.AuthorizationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorizationRepositoryAdapter implements AuthorizationRepository {

    private final JpaAuthorizationRepository authorizationRepository;

    private final JpaResetPasswordRequestRepository resetPasswordRequestRepository;

    public AuthorizationRepositoryAdapter(JpaAuthorizationRepository authorizationRepository, JpaResetPasswordRequestRepository resetPasswordRequestRepository) {
        this.authorizationRepository = authorizationRepository;
        this.resetPasswordRequestRepository = resetPasswordRequestRepository;
    }


    @Override
    public Optional<AuthPassUser> findByEmail(String email) {
        Optional<AuthPassUserEntity> userEntity = authorizationRepository.findByEmail(email);
        return userEntity.map(AuthPassUserMapper::toAuthPassUser);
    }

    @Override
    public Optional<AuthPassUser> findById(String id) {
        Optional<AuthPassUserEntity> userEntity = authorizationRepository.findById(id);
        return userEntity.map(AuthPassUserMapper::toAuthPassUser);
    }

    @Override
    public Optional<ResetPasswordRequest> findByRequestId(String id) {
        Optional<ResetPasswordRequestEntity> requestEntity = resetPasswordRequestRepository.findById(id);
        return requestEntity.map(ResetPasswordRequestMapper::toResetPasswordRequest);
    }

    @Override
    public void saveAuthPassUser(AuthPassUser user) {
        authorizationRepository.save(new AuthPassUserEntity(user));
    }

    @Override
    public void saveResetPasswordRequest(ResetPasswordRequest request) {
        resetPasswordRequestRepository.save(new ResetPasswordRequestEntity(request));
    }
}
