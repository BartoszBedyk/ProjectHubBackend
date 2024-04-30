package com.sensilabs.projecthub.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Technology {
    @NotNull(message = "ID cannot be null.")
    @Size(min = 36, max = 36, message = "ID must have 36 characters.")
    private String id;
    @NotBlank(message = "Name cannot be blank.")
    @Length(min = 1, max = 30, message = "Name must be between 1 and 30 characters.")
    private String name;
    @Length(min = 1, max = 200, message = "Description must be between 1 and 200 characters.")
    private String description;
}
