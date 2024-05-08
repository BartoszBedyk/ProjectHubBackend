package com.sensilabs.projecthub.project;

public class ProjectMapper {
    public static ProjectEntity toProjectEntity(Project project) {
        return ProjectEntity.builder()
                .id(project.getId())
                .name(project.getName())
                .createdById(project.getCreatedById())
                .createdOn(project.getCreatedOn())
                .description(project.getDescription())
                .build();
    }

    public static TechnologyEntity toTechnologyEntity(Technology technology, ProjectEntity projectEntity) {
        return TechnologyEntity.builder()
                .id(technology.getId())
                .description(technology.getDescription())
                .name(technology.getName())
                .project(projectEntity)
                .build();
    }

    public static Project toProject(ProjectEntity projectEntity) {
        return Project.builder()
                .id(projectEntity.getId())
                .createdById(projectEntity.getCreatedById())
                .createdOn(projectEntity.getCreatedOn())
                .description(projectEntity.getDescription())
                .name(projectEntity.getName())
                .technologies(projectEntity.getTechnologies().stream().map(ProjectMapper::toTechnology).toList())
                .build();
    }

    public static Technology toTechnology(TechnologyEntity technologyEntity) {
        return Technology.builder()
                .id(technologyEntity.getId())
                .name(technologyEntity.getName())
                .description(technologyEntity.getDescription())
                .build();
    }
}
