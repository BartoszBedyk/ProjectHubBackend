package com.sensilabs.projecthub.project;

import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectMember {
    private String userId;
    private String firstName;
    private String lastName;
    private String createdById;
    private Instant createdOn;
    private Role role;
    private String projectId;
    private List<String> environmentIds;
}
