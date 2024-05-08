package com.sensilabs.projecthub.login.pass.auth;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AuthPassUserPropsImpl implements AuthPassUserProps {
   private final Environment env;

    public AuthPassUserPropsImpl(Environment env) {
        this.env = env;
    }

    @Override
    public long getResetPasswordTokenExpiration() {
        return Long.parseLong(env.getRequiredProperty("app.login-auth-pass.reset-password-token-expiration-time-in-minutes"));
    }
}
