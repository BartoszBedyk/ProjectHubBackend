package com.sensilabs.projecthub.user.management.service;

import com.sensilabs.projecthub.activity.ActivityService;
import com.sensilabs.projecthub.activity.forms.DeleteUserForm;
import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.forms.EditUserForm;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	private final UserManagementRepository userManagementRepository;
	private final ActivityService activityService;

	public UserManagementServiceImpl(UserManagementRepository userManagementRepository, ActivityService activityService) {
		this.userManagementRepository = userManagementRepository;
        this.activityService = activityService;
    }

	@Override
	public SearchResponse<User> search(SearchForm form) {
		return userManagementRepository.search(form);
	}

	private User getOrThrow(String id) {
		return userManagementRepository.getNotDeleted(id).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
	}

	@Override
	public User get(String id) {
		return getOrThrow(id);
	}

	@Override
	public User save(CreateUserForm userRequest, String createdById) {
		User user = User.builder()
				.id(UUID.randomUUID().toString())
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.email(userRequest.getEmail())
				.createdOn(Instant.now())
				.createdById(createdById)
				.deletedOn(null)
				.deletedById(null)
				.isBlocked(false)
				.build();
		activityService.save(new com.sensilabs.projecthub.activity.forms.CreateUserForm(user.getId(), user.getFirstName(), user.getLastName()), createdById);
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
	public void delete(String id, String deletedById) {
		User user = getOrThrow(id);
		user.setDeletedOn(Instant.now());
		user.setDeletedById(deletedById);
		userManagementRepository.save(user);
		activityService.save(new DeleteUserForm(id, user.getFirstName(), user.getLastName()), deletedById);
	}
}
