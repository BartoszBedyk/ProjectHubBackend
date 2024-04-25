package com.sensilabs.projecthub.login.pass.auth;

import java.util.UUID;

public class TokenProviderMock implements TokenProvider{
    @Override
    public String generateToken(String userId) {
        return UUID.randomUUID().toString();
    }
}
