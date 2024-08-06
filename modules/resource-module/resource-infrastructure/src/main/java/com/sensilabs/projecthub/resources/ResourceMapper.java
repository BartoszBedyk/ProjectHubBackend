package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.resources.model.Resource;

public class ResourceMapper {
    public static ResourceEntity toResourceEntity(Resource resource) {
        return ResourceEntity.builder()
                .id(resource.getId())
                .name(resource.getName())
                .description(resource.getDescription())
                .value(resource.getValue())
                .resourceType(resource.getResourceType())
                .environmentId(resource.getEnvironmentId())
                .projectId(resource.getProjectId())
                .createdById(resource.getCreatedById())
                .createdOn(resource.getCreatedOn())
                .lastModifiedOn(resource.getLastModifiedOn())
                .deletedOn(resource.getDeletedOn())
                .deletedById(resource.getDeletedById())
                .build();
    }

    public static Resource toResource(ResourceEntity entity) {
        return Resource.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .value(entity.getValue())
                .resourceType(entity.getResourceType())
                .environmentId(entity.getEnvironmentId())
                .projectId(entity.getProjectId())
                .createdById(entity.getCreatedById())
                .createdOn(entity.getCreatedOn())
                .lastModifiedOn(entity.getLastModifiedOn())
                .deletedById(entity.getDeletedById())
                .deletedOn(entity.getDeletedOn())
                .build();
    }
}
