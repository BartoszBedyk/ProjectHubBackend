package com.sensilabs.projecthub.project;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "created_by_id")
    private String createdById;
    @ElementCollection
    @CollectionTable(name = "project_technologies", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "technology_id")
    private List<String> technologies;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProjectMemberEntity> members = new HashSet<>();
    @Column(name = "deletedOn")
    private Instant deletedOn;
    @Column(name = "deleted_by_id")
    private String deletedById;
}
