package com.sensilabs.projecthub.login.pass.auth.service;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.login.pass.auth.AuthPassUser;
import com.sensilabs.projecthub.login.pass.auth.PasswordEncoder;
import com.sensilabs.projecthub.login.pass.auth.ResetPasswordRequest;
import com.sensilabs.projecthub.login.pass.auth.TokenProvider;
import com.sensilabs.projecthub.login.pass.auth.forms.*;
import com.sensilabs.projecthub.login.pass.auth.repository.AuthorizationRepository;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final UserManagementService userManagementService;

    public AuthorizationServiceImpl(AuthorizationRepository authorizationRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, UserManagementService userManagementService) {
        this.authorizationRepository = authorizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userManagementService = userManagementService;
    }

    private AuthPassUser getByEmailOrThrowAuthPassUser(String email) {
        return authorizationRepository.findByEmail(email).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    private AuthPassUser getByIdOrThrowAuthPassUser(String id) {
        return authorizationRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }
    private ResetPasswordRequest getOrThrowResetPasswordRequest(String id) {
        return authorizationRepository.findByRequestId(id).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public AuthPassUser login(LoginForm loginRequest) {
            AuthPassUser user = getByEmailOrThrowAuthPassUser(loginRequest.getEmail());

            if (passwordEncoder.match(loginRequest.getPassword(), user.getPassword())) {
                tokenProvider.generateToken(user.getId());
                return user;
            }

        throw new ApplicationException(ErrorCode.WRONG_PASSWORD);
    }

    @Override
    public void createUser(CreateUserWithPasswordForm createUserRequest) {

        User user = userManagementService.save(createUserRequest);

        AuthPassUser userAuth = AuthPassUser.builder()
                .id(user.getId())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build();


        authorizationRepository.saveAuthPassUser(userAuth);
        // serwis do wysylki maila
    }


    @Override
    public void resetPassword(ResetPasswordForm resetPasswordRequest) {
        Instant time = Instant.now();
        AuthPassUser user = getByEmailOrThrowAuthPassUser(resetPasswordRequest.getEmail());
        ResetPasswordRequest request = ResetPasswordRequest.builder()
                .id(UUID.randomUUID().toString())
                .createdOn(time)
                .expiredOn(time.plus(15, ChronoUnit.MINUTES))
                .userId(user.getId())
                .build();

        authorizationRepository.saveResetPasswordRequest(request);
    }

    @Override
    public void checkResetPasswordToken(String token) {
        ResetPasswordRequest request = getOrThrowResetPasswordRequest(token);
        if (!request.getExpiredOn().isAfter(Instant.now())) {
            throw new ApplicationException(ErrorCode.LINK_EXPIRED);
        }
    }

    @Override
    public void resetPasswordConfirm(ResetPasswordConfirmForm resetPasswordConfirmForm) {
        ResetPasswordRequest request = getOrThrowResetPasswordRequest(resetPasswordConfirmForm.getRequestId());
        AuthPassUser user = getByIdOrThrowAuthPassUser(request.getUserId());

        checkResetPasswordToken(resetPasswordConfirmForm.getRequestId());

        user.setPassword(passwordEncoder.encode(resetPasswordConfirmForm.getPassword()));
        authorizationRepository.saveAuthPassUser(user);
    }

    @Override
    public void changePassword(ChangePasswordForm changePasswordForm) {
        AuthPassUser user = getByEmailOrThrowAuthPassUser(changePasswordForm.getEmail());

        if (passwordEncoder.match(changePasswordForm.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
            authorizationRepository.saveAuthPassUser(user);
        } else {
            throw new ApplicationException(ErrorCode.WRONG_PASSWORD);
        }
    }
}
