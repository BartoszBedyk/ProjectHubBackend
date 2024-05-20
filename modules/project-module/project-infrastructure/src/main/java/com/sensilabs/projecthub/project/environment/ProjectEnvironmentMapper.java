package com.sensilabs.projecthub.project.environment;

public class ProjectEnvironmentMapper {

    public static ProjectEnvironmentEntity toProjectEnvironmentEntity(ProjectEnvironment projectEnvironment) {
        return ProjectEnvironmentEntity.builder()
                .id(projectEnvironment.getId())
                .name(projectEnvironment.getName())
                .isEncrypted(projectEnvironment.isEncrypted())
                .projectId(projectEnvironment.getProjectId())
                .createdOn(projectEnvironment.getCreatedOn())
                .updatedOn(projectEnvironment.getUpdatedOn())
                .createdById(projectEnvironment.getCreatedById())
                .build();
    }

    public static ProjectEnvironment toProjectEnvironment(ProjectEnvironmentEntity projectEnvironmentEntity) {
        return ProjectEnvironment.builder()
                .id(projectEnvironmentEntity.getId())
                .name(projectEnvironmentEntity.getName())
                .isEncrypted(projectEnvironmentEntity.isEncrypted())
                .projectId(projectEnvironmentEntity.getProjectId())
                .createdOn(projectEnvironmentEntity.getCreatedOn())
                .updatedOn(projectEnvironmentEntity.getUpdatedOn())
                .createdById(projectEnvironmentEntity.getCreatedById())
                .build();
    }
}
