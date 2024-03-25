package com.sensilabs.projecthub.user.management;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserManagementRepositoryMock implements UserManagementRepository {
    Map<String, User> mockDB = new HashMap<>();

    @Override
    public SearchResponse<User> search(SearchForm form) {
        return null;
    }

    @Override
    public Optional<User> get(String id) {
        return Optional.ofNullable(mockDB.get(id));
    }

    @Override
    public User save(User user) {
        mockDB.put(user.getId(), user);
        return user;
    }

    @Override
    public User block(String id) {
        User user = mockDB.get(id);
        if (user != null) {
            user.setBlocked(true);
            mockDB.put(id, user);
        }
        return user;
    }

    @Override
    public User unBlock(String id) {
        User user = mockDB.get(id);
        if (user != null) {
            user.setBlocked(false);
            mockDB.put(id, user);
        }
        return user;
    }

    @Override
    public void delete(String id) {
        mockDB.remove(id);
    }
}
