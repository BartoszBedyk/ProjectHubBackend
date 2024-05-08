package com.sensilabs.projecthub.security;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SecurityProps {
    private final Environment env;

    public SecurityProps(Environment env) {
        this.env = env;
    }

    public String getSecretKey() {
        return env.getRequiredProperty("app.security.jwt.secret");
    }

    public long getTokenExpiration() {
        return Long.parseLong(env.getRequiredProperty("app.security.jwt.token-expiration-time-in-minutes"));
    }
}
