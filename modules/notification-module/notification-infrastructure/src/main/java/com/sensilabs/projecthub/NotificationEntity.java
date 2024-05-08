package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import com.sensilabs.projecthub.notification.model.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "notification")
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
    private String createdById;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private NotificationChannel channel;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "sent")
    private Boolean sent;

    @Column(name = "last_attempt_on")
    private Instant lastAttemptOn;

    @Column(name = "number_of_attempts")
    private Integer numberOfAttempts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notification", fetch = FetchType.EAGER)
    private List<NotificationParamEntity> params;
}
