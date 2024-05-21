package com.sensilabs.projecthub.project;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "project_member")
@IdClass(ProjectMemberPrimaryKey.class)
public class ProjectMemberEntity {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "created_by_id")
    private String createdById;
    @Column(name = "created_on")
    private Instant createdOn;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Id
    @Column(name = "project_id")
    private String projectId;
}
