package com.sensilabs.projecthub.project;

import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectDTO {
    private String projectId;
    private String projectName;
    private String projectDescription;
    private Instant projectCreatedOn;
    private String projectCreatedById;
    private List<TechnologyDTO> projectTechnologies;
}
