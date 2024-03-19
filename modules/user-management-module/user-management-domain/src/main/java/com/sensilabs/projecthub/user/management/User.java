package com.sensilabs.projecthub.user.management;

import lombok.*;

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
    private Date createdOn;
    private boolean isBlocked;

    // TODO reszta atrybutów
}
