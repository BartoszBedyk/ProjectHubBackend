package com.sensilabs.projecthub.activity.model;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Activity {
    private String id;
    private String createdById;
    private ActivityType type;
    private Date createdOn;
    private List<ActivityParam> params;

}
