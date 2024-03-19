package com.sensilabs.projecthub.user.management.service;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.forms.EditUserForm;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class DomainUserManagementService implements UserManagementService {

    private final UserManagementRepository userManagementRepository;

    public DomainUserManagementService(UserManagementRepository userManagementRepository) {
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    public SearchResponse<User> search(SearchForm form) {
        return null;
    }

    @Override
    public Optional<User> get(String id) {
        return userManagementRepository.findById(id);
    }

    @Override
    public User save(CreateUserForm userRequest) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .createdOn(userRequest.getCreatedOn())
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
        return userManagementRepository.block(id);
    }

    @Override
    public User unBlock(String id) {
        return userManagementRepository.unBlock(id);
    }

    @Override
    public void delete(String id) {
        userManagementRepository.delete(id);
    }
}
