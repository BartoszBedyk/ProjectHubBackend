package com.sensilabs.projecthub.login.pass.auth.service;

import com.sensilabs.projecthub.activity.ActivityService;
import com.sensilabs.projecthub.activity.forms.DeleteUserForm;
import com.sensilabs.projecthub.activity.forms.LogInFailedUserForm;
import com.sensilabs.projecthub.activity.forms.LogInSuccessUserForm;
import com.sensilabs.projecthub.activity.forms.LogOutUserForm;
import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.login.pass.auth.*;
import com.sensilabs.projecthub.login.pass.auth.forms.*;
import com.sensilabs.projecthub.login.pass.auth.repository.AuthorizationRepository;
import com.sensilabs.projecthub.notification.EmailingService;
import com.sensilabs.projecthub.notification.NotificationService;
import com.sensilabs.projecthub.notification.forms.AccountCreatedMailForm;
import com.sensilabs.projecthub.notification.forms.ResetPasswordMailFrom;
import com.sensilabs.projecthub.notification.model.Notification;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.service.UserManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final UserManagementService userManagementService;

    private final AuthPassUserProps props;

    private final EmailingService emailingService;
    private final NotificationService notificationService;

    private final ActivityService activityService;

    public AuthorizationServiceImpl(AuthorizationRepository authorizationRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, UserManagementService userManagementService, AuthPassUserProps props, EmailingService emailingService, NotificationService notificationService, ActivityService activityService) {
        this.authorizationRepository = authorizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userManagementService = userManagementService;
        this.props = props;
        this.emailingService = emailingService;
        this.notificationService = notificationService;
        this.activityService = activityService;
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
    public LoginResponse login(LoginForm loginRequest) {
        AuthPassUser user = getByEmailOrThrowAuthPassUser(loginRequest.getEmail());
        User userDetails = userManagementService.get(user.getId());

        if (userDetails.isBlocked()) {
            throw new ApplicationException(ErrorCode.USER_IS_BLOCKED);
        }

        if (passwordEncoder.match(loginRequest.getPassword(), user.getPassword())) {
            String token = tokenProvider.generateToken(user.getId());
            activityService.save(new LogInSuccessUserForm(user.getId(), userDetails.getFirstName(), userDetails.getLastName()), user.getId());
            return new LoginResponse(token);
        }
        activityService.save(new LogInFailedUserForm(user.getId(), userDetails.getFirstName(), userDetails.getLastName()), user.getId());
        throw new ApplicationException(ErrorCode.WRONG_LOGIN_OR_PASSWORD);
    }

    @Override
    public void createUser(CreateUserWithPasswordForm createUserRequest, String createdById) {

        User user = userManagementService.save(createUserRequest, createdById);

        AuthPassUser userAuth = AuthPassUser.builder()
                .id(user.getId())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build();


        authorizationRepository.saveAuthPassUser(userAuth);
        Notification notification = notificationService.save(new AccountCreatedMailForm(createUserRequest.getFirstName(), createUserRequest.getLastName(), createUserRequest.getEmail()), createdById);
        emailingService.send(notification);
        activityService.save(new com.sensilabs.projecthub.activity.forms.CreateUserForm(user.getId(), user.getFirstName(), user.getLastName()), createdById);
    }


    @Override
    public void resetPassword(ResetPasswordForm resetPasswordRequest) {
        Instant time = Instant.now();
        AuthPassUser authUser = getByEmailOrThrowAuthPassUser(resetPasswordRequest.getEmail());
        User user = userManagementService.get(authUser.getId());
        ResetPasswordRequest request = ResetPasswordRequest.builder()
                .id(UUID.randomUUID().toString())
                .createdOn(time)
                .expiredOn(time.plus(props.getResetPasswordTokenExpirationInMinutes(), ChronoUnit.MINUTES))
                .userId(authUser.getId())
                .build();

        authorizationRepository.saveResetPasswordRequest(request);
        String link = "http://localhost:3000/auth/reset-password/" + request.getId();
        Notification notification = notificationService.save(new ResetPasswordMailFrom(user.getFirstName(), user.getLastName(), resetPasswordRequest.getEmail(), link), "SYSTEM");
        emailingService.send(notification);
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
            throw new ApplicationException(ErrorCode.WRONG_LOGIN_OR_PASSWORD);
        }
    }

    @Override
    public void deleteUser(String userId) {
        User user = userManagementService.get(userId);
        activityService.save(new DeleteUserForm(userId, user.getFirstName(), user.getLastName()), userId);
    }

    @Override
    public void logout(String userId) {
        User user = userManagementService.get(userId);
        activityService.save(new LogOutUserForm(userId, user.getFirstName(), user.getLastName()), userId);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void createSysAdminOnStartup() {

        if (authorizationRepository.findByEmail(props.getSysAdminEmail()).isPresent()) {
            log.info("Method createSysAdminOnStartup, SysAdmin already exists");
            return;
        }

        User user = userManagementService.saveSysAdminOnStartup(new CreateUserForm(props.getSysAdminFirstName(), props.getSysAdminLastName(), props.getSysAdminEmail()));

        AuthPassUser userAuth = AuthPassUser.builder()
                .id(user.getId())
                .email(props.getSysAdminEmail())
                .password(passwordEncoder.encode(props.getSysAdminPassword()))
                .build();

        authorizationRepository.saveAuthPassUser(userAuth);
    }
}
