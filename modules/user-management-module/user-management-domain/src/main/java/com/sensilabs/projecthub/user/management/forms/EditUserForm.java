package com.sensilabs.projecthub.user.management.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserForm {

    @NotNull(message = "ID must not be null")
    //@Size(min = 36, max = 36, message = "\nID must be 36 characters long")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Wrong ID pattern!")
    private String id;

    @NotBlank(message = "First name must not be blank")
    @Length(min = 2, message = "First name must be at least 2 characters long")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Length(min = 2, message = "Last name must be at least 2 characters long")
    private String lastName;

    @NotBlank(message = "Email name must not be blank")
    @Email(message = "Email is invalid")
    private String email;
}
