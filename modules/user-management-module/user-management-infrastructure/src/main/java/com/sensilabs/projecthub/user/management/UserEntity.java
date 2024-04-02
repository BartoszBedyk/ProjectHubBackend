package com.sensilabs.projecthub.user.management;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by_id")
    private String createdById;

    @Column(name = "deleted_on")
    private Instant deletedOn;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    public UserEntity(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.createdOn = user.getCreatedOn();
        this.deletedOn = user.getDeletedOn();
        this.isBlocked = user.isBlocked();
    }
}
