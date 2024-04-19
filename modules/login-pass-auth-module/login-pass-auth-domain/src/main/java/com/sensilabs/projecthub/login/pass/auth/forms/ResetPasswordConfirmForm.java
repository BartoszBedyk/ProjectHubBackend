package com.sensilabs.projecthub.login.pass.auth.forms;

import com.sensilabs.projecthub.commons.annotations.FieldsMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordConfirmForm {

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @FieldsMatch(first = "password", second = "passwordRepeated", message = "Passwords must match")
    String passwordRepeated;

    @NotNull(message = "ID must not be null")
    @Size(min = 36, max = 36, message = "ID must be 36 characters long")
    String requestId;
}
