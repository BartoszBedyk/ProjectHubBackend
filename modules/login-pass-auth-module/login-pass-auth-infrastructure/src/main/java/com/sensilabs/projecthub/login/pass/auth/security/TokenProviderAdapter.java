package com.sensilabs.projecthub.login.pass.auth.security;

import com.sensilabs.projecthub.login.pass.auth.TokenProvider;
import com.sensilabs.projecthub.security.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class TokenProviderAdapter implements TokenProvider {

    private final JwtUtil jwtUtil;


    public TokenProviderAdapter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String generateToken(String userId) {
        return jwtUtil.generateToken(userId);
    }
}
