package com.sensilabs.projecthub.user.management;

public class UserMapper {

    public static User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .createdOn(userEntity.getCreatedOn())
                .createdById(userEntity.getCreatedById())
                .deletedOn(userEntity.getDeletedOn())
                .deletedById(userEntity.getDeletedById())
                .isBlocked(userEntity.isBlocked())
                .build();
    }
}
