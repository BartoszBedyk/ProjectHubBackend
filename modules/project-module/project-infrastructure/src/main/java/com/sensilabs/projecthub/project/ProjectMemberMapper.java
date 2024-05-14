package com.sensilabs.projecthub.project;

import java.util.List;

public class ProjectMemberMapper {
    public static ProjectMemberEntity toProjectMemberEntity(ProjectMember projectMember, ProjectEntity projectEntity) {
        return ProjectMemberEntity.builder()
                .id(projectMember.getId())
                .role(projectMember.getRole())
                .firstName(projectMember.getFirstName())
                .lastName(projectMember.getLastName())
                .createdById(projectMember.getCreatedById())
                .createdOn(projectMember.getCreatedOn())
                .project(projectEntity)
                .build();
    }

    public static ProjectMember toProjectMember(ProjectMemberEntity projectMemberEntity) {
        return ProjectMember.builder()
                .id(projectMemberEntity.getId())
                .role(projectMemberEntity.getRole())
                .firstName(projectMemberEntity.getFirstName())
                .lastName(projectMemberEntity.getLastName())
                .createdById(projectMemberEntity.getCreatedById())
                .createdOn(projectMemberEntity.getCreatedOn())
                .projectId(projectMemberEntity.getProject().getId())
                .build();
    }
}
