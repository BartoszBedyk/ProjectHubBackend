package com.sensilabs.projecthub.login.pass.auth.service;

import com.sensilabs.projecthub.login.pass.auth.AuthPassUser;
import com.sensilabs.projecthub.login.pass.auth.forms.*;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AuthorizationService {

    // LoginResponse(token)
    AuthPassUser login(LoginForm logInRequest);

    void createUser(CreateUserWithPasswordForm createUserRequest);

    // Tworzy record w db (id requestu, data stworzenia, data waznosci, id user(z auth service))
    void resetPassword(ResetPasswordForm resetPasswordRequest);

    void checkResetPasswordToken(String requestId);

    void resetPasswordConfirm(ResetPasswordConfirmForm resetPasswordConfirmForm);

    void changePassword(ChangePasswordForm changePasswordForm);
}
