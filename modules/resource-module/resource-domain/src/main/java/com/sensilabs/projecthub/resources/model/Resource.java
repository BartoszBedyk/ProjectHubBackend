package com.sensilabs.projecthub.resources.model;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Resource {

    private String id;
    private String name;
    private String description;
    private String value;
    private ResourceType resourceType;
    private String environmentId;
    private String projectId;
    private String createdById;
    private Instant createdOn;
    private Instant lastModifiedOn;



}
