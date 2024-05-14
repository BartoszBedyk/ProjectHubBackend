package com.sensilabs.projecthub.login.pass.auth;

import com.sensilabs.projecthub.login.pass.auth.forms.ChangePasswordForm;
import com.sensilabs.projecthub.login.pass.auth.service.AuthorizationService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
public class ChangePasswordController {

    private final AuthorizationService authorizationService;

    public ChangePasswordController(AuthorizationService service) {
        this.authorizationService = service;
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordForm form) {
        authorizationService.changePassword(form);
    }
}
