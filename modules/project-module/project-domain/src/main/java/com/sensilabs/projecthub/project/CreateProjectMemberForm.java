package com.sensilabs.projecthub.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectMemberForm {
    @NotBlank(message = "First name cannot be blank.")
    @Length(min = 1, max = 30, message = "First name must be between 1 and 30 characters.")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank.")
    @Length(min = 1, max = 50, message = "Last name must be between 1 and 50 characters.")
    private String lastName;
    @NotNull(message = "Role cannot be null.")
    private Role role;
}
