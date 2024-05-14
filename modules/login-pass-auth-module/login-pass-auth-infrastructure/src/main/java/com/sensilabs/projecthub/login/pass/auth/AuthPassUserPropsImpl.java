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
    public long getResetPasswordTokenExpirationInMinutes() {
        return Long.parseLong(env.getRequiredProperty("app.loginPassAuth.resetPasswordTokenExpirationTimeInMinutes"));
    }

    @Override
    public String getSysAdminFirstName() {
        return env.getRequiredProperty("app.loginPassAuth.sysAdminFirstName");
    }

    @Override
    public String getSysAdminLastName() {
        return env.getRequiredProperty("app.loginPassAuth.sysAdminLastName");
    }

    @Override
    public String getSysAdminEmail() {
        return env.getRequiredProperty("app.loginPassAuth.sysAdminEmail");
    }

    @Override
    public String getSysAdminPassword() {
        return env.getRequiredProperty("app.loginPassAuth.sysAdminPassword");
    }
}
