package com.sensilabs.projecthub.login.pass.auth;

public interface TokenProvider {
    String generateToken(String userId);
}
