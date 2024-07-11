package com.sensilabs.projecthub.project;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    private String id;
    private String name;
    private String description;
    private Instant createdOn;
    private String createdById;
    private List<String> technologies;
    private String deletedById;
    private Instant deletedOn;
}
