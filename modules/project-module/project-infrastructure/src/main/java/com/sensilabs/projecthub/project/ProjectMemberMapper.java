package com.sensilabs.projecthub.project;

public class ProjectMemberMapper {
    public static ProjectMemberEntity toProjectMemberEntity(ProjectMember projectMember) {
        return ProjectMemberEntity.builder()
                .userId(projectMember.getUserId())
                .role(projectMember.getRole())
                .firstName(projectMember.getFirstName())
                .lastName(projectMember.getLastName())
                .createdById(projectMember.getCreatedById())
                .createdOn(projectMember.getCreatedOn())
                .projectId(projectMember.getProjectId())
                .build();
    }

    public static ProjectMember toProjectMember(ProjectMemberEntity projectMemberEntity) {
        return ProjectMember.builder()
                .userId(projectMemberEntity.getUserId())
                .role(projectMemberEntity.getRole())
                .firstName(projectMemberEntity.getFirstName())
                .lastName(projectMemberEntity.getLastName())
                .createdById(projectMemberEntity.getCreatedById())
                .createdOn(projectMemberEntity.getCreatedOn())
                .projectId(projectMemberEntity.getProjectId())
                .build();
    }
}
