package com.sensilabs.projecthub.user.management;

public class UserMapper {

    public static User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .createdOn(userEntity.getCreatedOn())
                .createdById(userEntity.getCreatedById())
                .deletedOn(userEntity.getDeletedOn())
                .isBlocked(userEntity.isBlocked())
                .build();
    }
}
