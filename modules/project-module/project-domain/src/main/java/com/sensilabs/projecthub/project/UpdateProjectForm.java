package com.sensilabs.projecthub.project;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectForm {
    @NotNull(message = "ID cannot be null.")
    @Size(min = 36, max = 36, message = "ID must have 36 characters.")
    private String id;
    @NotBlank(message = "Name cannot be blank.")
    @Length(min = 1, max = 30, message = "Name must be between 1 and 30 characters.")
    private String name;
    @Length(min = 1, max = 200, message = "Description must be between 1 and 200 characters.")
    private String description;
    @NotEmpty(message = "Technology list cannot be empty")
    @NotNull(message = "Technology list cannot be null")
    @Valid
    private List<Technology> technologyList;
}