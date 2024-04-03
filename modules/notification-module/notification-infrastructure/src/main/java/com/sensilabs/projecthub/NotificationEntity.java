package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import com.sensilabs.projecthub.notification.model.NotificationParam;
import com.sensilabs.projecthub.notification.model.NotificationType;
import jakarta.annotation.Priority;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;


    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "created_by_id")
    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private NotificationChannel channel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notification")
    private List<NotificationParamEntity> params;
}
