package com.sensilabs.projecthub.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

import com.sensilabs.projecthub.activity.model.ActivityType;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activity", fetch = FetchType.EAGER)
    private List<ActivityParamEntity> params;

}
