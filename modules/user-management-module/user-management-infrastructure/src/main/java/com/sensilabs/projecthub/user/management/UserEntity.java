package com.sensilabs.projecthub.user.management;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by_id")
    private String createdById;

    @Column(name = "deleted_on")
    private Instant deletedOn;

    @Column(name = "deleted_by_id")
    private String deletedById;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    public UserEntity(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.createdById = user.getCreatedById();
        this.createdOn = user.getCreatedOn();
        this.deletedOn = user.getDeletedOn();
        this.deletedById = user.getDeletedById();
        this.isBlocked = user.isBlocked();
    }
}
