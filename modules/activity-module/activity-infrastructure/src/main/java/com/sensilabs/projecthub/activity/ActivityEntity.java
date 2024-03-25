package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "activity")
public class ActivityEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "created_by_id")
    private String createdById;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ActivityType type;

    @Column(name = "created_on")
    private Instant createdOn;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activity")
    private List<ActivityParamEntity> params;

}
