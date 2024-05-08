package com.sensilabs.projecthub.login.pass.auth;

import com.sensilabs.projecthub.login.pass.auth.forms.*;
import com.sensilabs.projecthub.login.pass.auth.repository.AuthorizationRepository;
import com.sensilabs.projecthub.login.pass.auth.service.AuthorizationService;
import com.sensilabs.projecthub.login.pass.auth.service.AuthorizationServiceImpl;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import com.sensilabs.projecthub.user.management.service.UserManagementService;
import com.sensilabs.projecthub.user.management.service.UserManagementServiceImpl;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class AuthorizationServiceTest {
    AuthorizationRepository repository = new AuthorizationRepositoryMock();
    PasswordEncoder encoder = new PasswordEncoderMock();
    TokenProvider provider = new TokenProviderMock();

    UserManagementRepository userManagementRepository = new UserManagementRepositoryMock();
    UserManagementService userManagementService = new UserManagementServiceImpl(userManagementRepository);

    AuthPassUserProps props = new AuthPassUserPropsMock();

    AuthorizationService service = new AuthorizationServiceImpl(repository, encoder, provider, userManagementService, props);

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    private <T> void genericViolationSet(T form) {
        Set<ConstraintViolation<T>> violations = validator.validate(form);

        assertThrows(ConstraintViolationException.class, () -> {
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException("Validation failed", violations);
            }
        });
    }

    @BeforeEach
    void setup() {
        CreateUserWithPasswordForm form = new CreateUserWithPasswordForm();
        form.setFirstName("Kacper");
        form.setLastName("Koncki");
        form.setEmail("test@test.pl");
        form.setPassword("password123");
        service.createUser(form);
    }
    @Test
    void successfulLogInTest() {
        LoginResponse response = service.login(new LoginForm("test@test.pl", "password123"));

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("Bearer", response.getType());
    }

    @Test
    void userNotFoundTest() {
        assertThrows(RuntimeException.class, () -> service.login(new LoginForm("wrongemail@test.pl", "password123")));
    }

    @Test
    void incorrectPasswordTest() {
        assertThrows(RuntimeException.class, () -> service.login(new LoginForm("test@test.pl", "wrongpassword")));
    }

    @Test
    void resetPasswordTest() {
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm("test@test.pl");
        service.resetPassword(resetPasswordForm);

        Optional<AuthPassUser> user = repository.findByEmail("test@test.pl");
        assertTrue(user.isPresent());

        Optional<ResetPasswordRequest> resetPasswordRequest = AuthorizationRepositoryMock.findRequestByUserId(user.get().getId());
        assertTrue(resetPasswordRequest.isPresent());

        ResetPasswordRequest request = resetPasswordRequest.get();
        assertNotNull(request.getId());
        assertNotNull(request.getCreatedOn());
        assertNotNull(request.getExpiredOn());
        assertEquals(request.getUserId(), user.get().getId());
    }

    @Test
    void checkResetPasswordTokensByIdTest() {
        Instant time = Instant.now();
        repository.saveResetPasswordRequest(new ResetPasswordRequest("UUID1", time, time.plus(15, ChronoUnit.MINUTES), "UUID2"));

        assertThrows(RuntimeException.class, () -> service.checkResetPasswordToken("UUID2"));
    }

    @Test
    void checkResetPasswordTokensByDateTest() {
        Instant time = Instant.now();
        repository.saveResetPasswordRequest(new ResetPasswordRequest("UUID1", time, time.minus(15, ChronoUnit.MINUTES), "UUID2"));

        assertThrows(RuntimeException.class, () -> service.checkResetPasswordToken("UUID1"));
    }

    @Test
    void resetPasswordConfirmTest() {
        Optional<AuthPassUser> user = repository.findByEmail("test@test.pl");
        assertTrue(user.isPresent());

        Instant time = Instant.now();
        repository.saveResetPasswordRequest(new ResetPasswordRequest("UUID1", time, time.plus(15, ChronoUnit.MINUTES), user.get().getId()));
        service.resetPasswordConfirm(new ResetPasswordConfirmForm("newPassword", "newPassword", "UUID1"));

        assertEquals("newPassword", user.get().getPassword());
    }

    @Test
    void changePasswordTest() {
        service.changePassword(new ChangePasswordForm("test@test.pl", "password123", "newPassword"));

        Optional<AuthPassUser> user = repository.findByEmail("test@test.pl");
        assertTrue(user.isPresent());
        assertEquals("newPassword", user.get().getPassword());
    }

    @Test
    void validateChangePasswordFormTest() {
        ChangePasswordForm form = new ChangePasswordForm("test.pl", "pass", "newPassword");

        genericViolationSet(form);
    }

    @Test
    void validateCreateUserWithPasswordFormTest() {
        CreateUserWithPasswordForm form = new CreateUserWithPasswordForm("pass");

        genericViolationSet(form);
    }

    @Test
    void validateLogInFormTest() {
        LoginForm form = new LoginForm("test", "pass");

        genericViolationSet(form);
    }

    @Test
    void validateResetPasswordConfirmFormTest() {
        ResetPasswordConfirmForm form = new ResetPasswordConfirmForm("pass", "pass123", "UUID1");

        genericViolationSet(form);
    }

    @Test
    void validateResetPasswordFormTest() {
        ResetPasswordForm form = new ResetPasswordForm("test.pl");

        genericViolationSet(form);
    }

}