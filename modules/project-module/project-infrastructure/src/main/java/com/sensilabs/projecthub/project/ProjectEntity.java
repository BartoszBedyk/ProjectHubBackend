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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
    private List<TechnologyEntity> technologies;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Set<ProjectMemberEntity> members = new HashSet<>();
}
