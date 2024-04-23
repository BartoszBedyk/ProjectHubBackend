package com.sensilabs.projecthub.login.pass.auth.service;

import com.sensilabs.projecthub.login.pass.auth.AuthPassUser;
import com.sensilabs.projecthub.login.pass.auth.forms.*;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AuthorizationService {

    // LoginResponse(token)
    AuthPassUser login(@Valid LoginForm logInRequest);

    void createUser(@Valid CreateUserWithPasswordForm createUserRequest);

    // Tworzy record w db (id requestu, data stworzenia, data waznosci, id user(z auth service))
    void resetPassword(@Valid ResetPasswordForm resetPasswordRequest);

    void checkResetPasswordToken(String requestId);

    void resetPasswordConfirm(@Valid ResetPasswordConfirmForm resetPasswordConfirmForm);

    void changePassword(@Valid ChangePasswordForm changePasswordForm);
}
