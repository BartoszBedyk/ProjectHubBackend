package com.sensilabs.projecthub.project.environment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "project_environment")
public class ProjectEnvironmentEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_encrypted")
    private boolean isEncrypted;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "deleted_on")
    private Instant deletedOn;

    @Column(name = "deleted_by_id")
    private String deletedById;

    @Column(name = "created_by_id")
    private String createdById;
}
