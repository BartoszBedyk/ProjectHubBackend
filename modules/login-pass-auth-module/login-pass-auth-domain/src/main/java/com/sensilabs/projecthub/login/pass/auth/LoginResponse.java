package com.sensilabs.projecthub.login.pass.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    public String token;
    public String type;

    public LoginResponse(String token) {
        this.token = token;
        this.type = "Bearer";
    }
}
