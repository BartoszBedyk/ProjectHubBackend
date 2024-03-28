package com.sensilabs.projecthub.user.management;

import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private Instant createdOn;
    private String createdById;
    private Instant deletedOn;
    private boolean isBlocked;

    // TODO reszta atrybutów
}
