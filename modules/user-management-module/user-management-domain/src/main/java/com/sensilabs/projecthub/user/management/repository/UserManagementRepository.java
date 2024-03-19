package com.sensilabs.projecthub.user.management.repository;

import com.sensilabs.projecthub.user.management.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserManagementRepository {

    Optional<User> findById(String id);
    User save(User user);
    User block(String id);
    User unBlock(String id);
    void delete(String id);
}
