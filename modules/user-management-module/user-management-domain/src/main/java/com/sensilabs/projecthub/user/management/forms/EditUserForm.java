package com.sensilabs.projecthub.user.management.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserForm {
    private String id;
    private String firstName;
    private String lastName;
    // TODO dodać resztę atrybutów
}
