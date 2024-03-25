package com.sensilabs.projecthub.user.management.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.forms.EditUserForm;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;

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
	public User get(String id) {
		return getOrThrow(id);
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
		User user = getOrThrow(id);
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		return userManagementRepository.save(user);
	}

	@Override
	public User block(String id) {
		User user = getOrThrow(id);
		user.setBlocked(true);
		return userManagementRepository.save(user);
	}

	@Override
	public User unBlock(String id) {
		User user = getOrThrow(id);
		user.setBlocked(false);
		return userManagementRepository.save(user);
	}

	@Override
	public void delete(String id) {
		userManagementRepository.delete(id);
	}

	private User getOrThrow(String id) {
		return userManagementRepository.get(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found!"));
	}
}
