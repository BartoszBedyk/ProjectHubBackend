package com.sensilabs.projecthub.resources.forms;

import com.sensilabs.projecthub.resources.model.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CreateLinkResourceForm implements ResourceForm{

    @NotNull(message = "Name of the resource cannot be null.")
    @Size(min = 1, max = 50, message = "Length of name of the resource cannot be less than 1 and longer than 50.")
    @NotBlank(message = "Name of the resource cannot be null.")
    private String name;

    @Size(max = 5000, message = "Description length cannot be longer than 5000 characters.")
    private String description;

    @NotNull(message = "Value of the resource cannot be null.")
    @NotBlank(message = "Value of the resource cannot be null.")
    @Size(min = 1, max = 200, message = "Length of value of the resource cannot be less than 1 and longer than 200.")
    private String value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getValue() {
        return value;
    }
    @Override
    public ResourceType getType() {
        return ResourceType.LINK;
    }


}
