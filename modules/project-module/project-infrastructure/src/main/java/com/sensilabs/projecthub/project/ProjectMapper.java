package com.sensilabs.projecthub.project;

public class ProjectMapper {
    public static ProjectEntity toProjectEntity(Project project) {
        return ProjectEntity.builder()
                .id(project.getId())
                .name(project.getName())
                .createdById(project.getCreatedById())
                .createdOn(project.getCreatedOn())
                .description(project.getDescription())
                .technologies(project.getTechnologies())
                .deletedById(project.getDeletedById())
                .deletedOn(project.getDeletedOn())
                .build();
    }

    public static Project toProject(ProjectEntity projectEntity) {
        return Project.builder()
                .id(projectEntity.getId())
                .createdById(projectEntity.getCreatedById())
                .createdOn(projectEntity.getCreatedOn())
                .description(projectEntity.getDescription())
                .name(projectEntity.getName())
                .technologies(projectEntity.getTechnologies())
                .deletedOn(projectEntity.getDeletedOn())
                .deletedById(projectEntity.getDeletedById())
                .build();
    }
}
