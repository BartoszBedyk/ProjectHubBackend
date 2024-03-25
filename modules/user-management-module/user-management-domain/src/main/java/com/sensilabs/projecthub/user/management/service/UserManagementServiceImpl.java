package com.sensilabs.projecthub.user.management.service;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.forms.EditUserForm;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserManagementRepository userManagementRepository;

    public UserManagementServiceImpl(UserManagementRepository userManagementRepository) {
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    public SearchResponse<User> search(SearchForm form) {
        return null;
    }

    @Override
    public Optional<User> get(String id) {
        return userManagementRepository.get(id);
    }

    @Override
    public User save(CreateUserForm userRequest) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .createdOn(Instant.now())
                .isBlocked(false)
                .build();
        return userManagementRepository.save(user);
    }

    @Override
    public User update(EditUserForm userRequest) {
        User user = get(userRequest.getId()).orElseThrow(() -> new RuntimeException("User with id " + userRequest.getId() + " not found!")); // TODO custom exception?
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        return userManagementRepository.save(user);
    }

    @Override
    public User block(String id) {
        User user = get(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found!"));
        user.setBlocked(true);
        return userManagementRepository.save(user);
    }

    @Override
    public User unBlock(String id) {
        User user = get(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found!"));
        user.setBlocked(false);
        return userManagementRepository.save(user);
    }

    @Override
    public void delete(String id) {
        userManagementRepository.delete(id);
    }
}
