package com.sensilabs.projecthub.login.pass.auth;

public class AuthPassUserPropsMock implements AuthPassUserProps{
    @Override
    public long getResetPasswordTokenExpiration() {
        return 15;
    }
}
