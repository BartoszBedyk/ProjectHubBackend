package com.sensilabs.projecthub.login.pass.auth.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordForm {
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email is invalid")
    private String email;
}
