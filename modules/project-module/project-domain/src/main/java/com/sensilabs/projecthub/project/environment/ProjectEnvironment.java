package com.sensilabs.projecthub.project.environment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectEnvironment {
    private String id;
    private String name;
    private boolean isEncrypted;
    private String projectId;
    private Instant createdOn;
    private Instant updatedOn;
    private String createdById;
}
