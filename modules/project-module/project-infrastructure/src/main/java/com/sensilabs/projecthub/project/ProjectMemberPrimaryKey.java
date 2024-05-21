package com.sensilabs.projecthub.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberPrimaryKey implements Serializable {
    private String userId;
    private String projectId;
}