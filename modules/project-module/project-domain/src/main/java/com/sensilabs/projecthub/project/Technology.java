package com.sensilabs.projecthub.project;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Technology {
    private String id;
    private String name;
    private String description;
}
