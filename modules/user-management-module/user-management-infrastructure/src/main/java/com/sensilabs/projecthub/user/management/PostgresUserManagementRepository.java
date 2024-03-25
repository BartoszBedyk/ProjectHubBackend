package com.sensilabs.projecthub.user.management;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostgresUserManagementRepository implements UserManagementRepository {

    private final JpaUserManagementRepository userManagementRepository;

    @Autowired
    public PostgresUserManagementRepository(JpaUserManagementRepository userManagementRepository) {
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    public SearchResponse<User> search(SearchForm form) {
        return null;
    }

    @Override
    public Optional<User> get(String id) {
        Optional<UserEntity> userEntity = userManagementRepository.findById(id);
        return userEntity.map(UserMapper::toUser);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userManagementRepository.save(new UserEntity(user));
        return UserMapper.toUser(userEntity);
    }

    @Override
    public User block(String id) {
        return null;
    }

    @Override
    public User unBlock(String id) {
        return null;
    }

    @Override
    public void delete(String id) {
        userManagementRepository.deleteById(id);
    }
}
