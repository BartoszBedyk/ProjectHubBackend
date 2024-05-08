package com.sensilabs.projecthub.project;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectMember {
    private String id;
    private String firstName;
    private String lastName;
    private String createdById;
    private Instant createdOn;
    private Role role;
}
