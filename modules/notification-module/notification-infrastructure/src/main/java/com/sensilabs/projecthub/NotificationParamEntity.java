package com.sensilabs.projecthub;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificaation_param")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NotificationParamEntity {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    private NotificationEntity notification;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private String value;
}
