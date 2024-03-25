package com.sensilabs.projecthub.user.management.repository;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.User;

import java.util.Optional;

public interface UserManagementRepository {

    SearchResponse<User> search(SearchForm form);
    Optional<User> get(String id);
    User save(User user);
    User block(String id);
    User unBlock(String id);
    void delete(String id);
}
