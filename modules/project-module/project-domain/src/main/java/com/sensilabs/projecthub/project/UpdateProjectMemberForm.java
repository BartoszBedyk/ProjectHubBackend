package com.sensilabs.projecthub.project;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectMemberForm {
    @NotNull(message = "ID cannot be null.")
    @Size(min = 36, max = 36, message = "ID must have 36 characters.")
    private String userId;
    @NotNull(message = "ID cannot be null.")
    @Size(min = 36, max = 36, message = "ID must have 36 characters.")
    private String projectId;
    @NotNull(message = "Role cannot be null.")
    private Role role;
    @NotNull(message = "Environments IDs cannot be null.")
    @Size(min = 1, message = "At least one environment ID must be provided.")
    List<@Size(min = 36, max = 36, message = "ID must have 36 characters.") String> environmentIds;
}
