package com.sensilabs.projecthub.project;

public class TechnologyMapper {

    public static TechnologyEntity toTechnologyEntity(Technology technology) {
        return TechnologyEntity.builder()
                .id(technology.getId())
                .description(technology.getDescription())
                .name(technology.getName())
                .build();
    }

    public static Technology toTechnology(TechnologyEntity technologyEntity) {
        return Technology.builder()
                .id(technologyEntity.getId())
                .description(technologyEntity.getDescription())
                .name(technologyEntity.getName())
                .build();
    }
}
