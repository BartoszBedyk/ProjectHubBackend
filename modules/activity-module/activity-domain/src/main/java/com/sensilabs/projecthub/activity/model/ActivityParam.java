package com.sensilabs.projecthub.activity.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityParam {

    private String name;
    private String value;
    private ActivityType type;

}
