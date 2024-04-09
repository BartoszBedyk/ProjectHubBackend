package com.sensilabs.projecthub.activity.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityParam {

    private String id;
    private String name;
    private String value;
}
