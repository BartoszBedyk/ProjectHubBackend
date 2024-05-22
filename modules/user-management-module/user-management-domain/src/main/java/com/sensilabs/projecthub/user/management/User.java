package com.sensilabs.projecthub.user.management;

import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Instant createdOn;
    private String createdById;
    private Instant deletedOn;
    private String deletedById;
    private boolean isBlocked;
}
