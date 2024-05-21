package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.resources.model.ResourceType;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "resource")

public class ResourceEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    @Column(name = "environment_id")
    private String environmentId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "created_by_id")
    private String createdById;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "last_modified_on")
    private Instant lastModifiedOn;
}
