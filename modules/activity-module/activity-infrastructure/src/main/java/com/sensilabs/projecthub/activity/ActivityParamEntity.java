package com.sensilabs.projecthub.activity;

import com.sensilabs.projecthub.activity.ActivityEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "activity_param")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ActivityParamEntity {
    @Id
    @Column(name = "activity_param_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    private ActivityEntity activity;

    private String paramName;
    private String paramValue;
}
