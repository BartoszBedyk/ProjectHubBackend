package com.sensilabs.projecthub.user.management.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserForm {
    private String firstName;
    private String lastName;
    private Date createdOn;

    // TODO dodać resztę atrybutów
}
