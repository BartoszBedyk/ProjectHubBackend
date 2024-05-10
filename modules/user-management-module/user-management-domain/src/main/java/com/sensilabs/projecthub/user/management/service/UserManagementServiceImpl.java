package com.sensilabs.projecthub.user.management.service;

import com.sensilabs.projecthub.commons.*;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.forms.EditUserForm;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class UserManagementServiceImpl implements UserManagementService {

	private final UserManagementRepository userManagementRepository;

	private final LoggedUser loggedUser;

	public UserManagementServiceImpl(UserManagementRepository userManagementRepository, LoggedUser loggedUser) {
		this.userManagementRepository = userManagementRepository;
        this.loggedUser = loggedUser;
    }

	@Override
	public SearchResponse<User> search(SearchForm form) {
		return null;
	}

	private User getOrThrow(String id) {
		return userManagementRepository.getNotDeleted(id).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
	}

	@Override
	public User get(String id) {
		log.info("Method get(), LoggedUser {}", loggedUser.getUserId());
		return getOrThrow(id);
	}

	@Override
	public User save(CreateUserForm userRequest) {
		User user = User.builder()
				.id(UUID.randomUUID().toString())
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.email(userRequest.getEmail())
				.createdOn(Instant.now())
				.createdById(loggedUser.getUserId())
				.isBlocked(false)
				.build();

		return userManagementRepository.save(user);
	}

	@Override
	public User saveSysAdminOnStartup(CreateUserForm userRequest) {
		User user = User.builder()
				.id(UUID.randomUUID().toString())
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.email(userRequest.getEmail())
				.createdOn(Instant.now())
				.createdById("SYSTEM")
				.isBlocked(false)
				.build();

		return userManagementRepository.save(user);
	}

	@Override
	public User update(EditUserForm userRequest) {
		User user = getOrThrow(userRequest.getId());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail());

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
		User user = getOrThrow(id);
		user.setDeletedOn(Instant.now());
		userManagementRepository.save(user);
	}
}
