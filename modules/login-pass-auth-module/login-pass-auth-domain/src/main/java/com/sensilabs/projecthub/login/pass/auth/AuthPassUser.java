package com.sensilabs.projecthub.login.pass.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthPassUser {
    private String id;
    private String email;
    private String password;
}
