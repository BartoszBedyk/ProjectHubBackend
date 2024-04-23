package com.sensilabs.projecthub.login.pass.auth;

public class ResetPasswordRequestMapper {

    public static ResetPasswordRequest toResetPasswordRequest(ResetPasswordRequestEntity resetPasswordRequestEntity) {
        return ResetPasswordRequest.builder()
                .id(resetPasswordRequestEntity.getId())
                .createdOn(resetPasswordRequestEntity.getCreatedOn())
                .expiredOn(resetPasswordRequestEntity.getExpiredOn())
                .userId(resetPasswordRequestEntity.getUserId())
                .build();
    }
}
