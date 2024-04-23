package com.sensilabs.projecthub.login.pass.auth;

import org.springframework.stereotype.Component;

@Component
public class TokenProviderAdapter implements TokenProvider{

    // TODO correct implementation
    @Override
    public String generateToken(String userId) {
        return "token";
    }
}
