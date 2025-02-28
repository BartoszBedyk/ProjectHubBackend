package com.sensilabs.projecthub.activity.model;

import lombok.*;
import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Activity {
    private String id;
    private String createdById;
    private ActivityType type;
    private Instant createdOn;
    private List<ActivityParam> params;
}
