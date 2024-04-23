package com.sensilabs.projecthub.login.pass.auth;

public class AuthPassUserMapper {

    public static AuthPassUser toAuthPassUser(AuthPassUserEntity authPassUserEntity) {
        return AuthPassUser.builder()
                .id(authPassUserEntity.getId())
                .email(authPassUserEntity.getEmail())
                .password(authPassUserEntity.getPassword())
                .build();
    }
}
