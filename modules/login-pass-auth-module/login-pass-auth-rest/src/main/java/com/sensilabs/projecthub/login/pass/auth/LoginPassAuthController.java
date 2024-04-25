package com.sensilabs.projecthub.login.pass.auth;

import com.sensilabs.projecthub.login.pass.auth.forms.*;
import com.sensilabs.projecthub.login.pass.auth.service.AuthorizationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login-pass-auth/test")
public class LoginPassAuthController {

    private final AuthorizationService authorizationService;

    public LoginPassAuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public AuthPassUser login(@RequestBody LoginForm form) {
        return authorizationService.login(form);
    }

    @PostMapping("/register")
    public void createUser(@RequestBody CreateUserWithPasswordForm form) {
        authorizationService.createUser(form);
    }

    @PostMapping("/reset-password-request")
    public void resetPassword(@RequestBody ResetPasswordForm form) {
        authorizationService.resetPassword(form);
    }

    @PutMapping("/reset-password")
    public void resetPasswordConfirm(@RequestBody ResetPasswordConfirmForm form) {
        authorizationService.resetPasswordConfirm(form);
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordForm form) {
        authorizationService.changePassword(form);
    }
}
