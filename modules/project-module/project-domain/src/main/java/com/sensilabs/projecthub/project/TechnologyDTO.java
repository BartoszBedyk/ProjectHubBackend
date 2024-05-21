package com.sensilabs.projecthub.project;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechnologyDTO {
    private String technologyId;
    private String technologyName;
    private String technologyDescription;
}