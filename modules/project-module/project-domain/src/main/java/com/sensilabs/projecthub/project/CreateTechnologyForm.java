package com.sensilabs.projecthub.project;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTechnologyForm {
    @NotBlank(message = "Name cannot be blank.")
    @Length(min = 1, max = 30, message = "Name must be between 1 and 30 characters.")
    private String name;
    @Length(min = 1, max = 200, message = "Description must be between 1 and 200 characters.")
    private String description;
}
