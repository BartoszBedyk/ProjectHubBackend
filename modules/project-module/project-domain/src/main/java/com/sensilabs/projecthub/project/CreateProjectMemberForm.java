package com.sensilabs.projecthub.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

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
    @NotNull(message = "ID cannot be null.")
    @Size(min = 36, max = 36, message = "ID must have 36 characters.")
    private String projectId;
    @NotNull(message = "ID cannot be null.")
    @Size(min = 36, max = 36, message = "ID must have 36 characters.")
    private String userId;
    @NotNull(message = "Environments IDs cannot be null.")
    @Size(min = 1, message = "At least one environment ID must be provided.")
    List<@Size(min = 36, max = 36, message = "ID must have 36 characters.") String> environmentIds;
}
