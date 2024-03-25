package com.sensilabs.projecthub.activity.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityParam {

    private String name;
    private String value;
    private ActivityType type;

}
