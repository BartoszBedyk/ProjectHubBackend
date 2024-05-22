package com.sensilabs.projecthub.project.environment.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectEnvironmentForm {
    @NotNull(message = "ID cannot be null.")
    @Size(min = 36, max = 36, message = "ID must have 36 characters.")
    private String id;

    @NotBlank(message = "Name cannot be blank.")
    @Length(min = 1, max = 30, message = "Name must be between 1 and 30 characters.")
    private String name;

    @NotNull(message = "isEncrypted cannot be null")
    private boolean isEncrypted;
}
