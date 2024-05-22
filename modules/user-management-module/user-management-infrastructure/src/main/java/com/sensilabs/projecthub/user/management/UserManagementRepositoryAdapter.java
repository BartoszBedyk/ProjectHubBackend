package com.sensilabs.projecthub.user.management;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import com.sensilabs.projecthub.utils.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserManagementRepositoryAdapter implements UserManagementRepository {

    private final JpaUserManagementRepository userManagementRepository;

    @Autowired
    public UserManagementRepositoryAdapter(JpaUserManagementRepository userManagementRepository) {
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    public SearchResponse<User> search(SearchForm form) {
        Specification<UserEntity> specification = SearchSpecification.buildSpecification(form.getCriteria());
        Page<UserEntity> userPage = userManagementRepository.findAll(specification, SearchSpecification.getPageRequest(form));
        return SearchResponse.<User>builder()
                .items(userPage.getContent().stream()
                        .map(UserMapper::toUser)
                        .collect(Collectors.toList()))
                .total(userPage.getTotalElements())
                .build();
    }

    @Override
    public Optional<User> get(String id) {
        Optional<UserEntity> userEntity = userManagementRepository.findById(id);
        return userEntity.map(UserMapper::toUser);
    }

    @Override
    public Optional<User> getNotDeleted(String id) {
        return userManagementRepository.findByIdAndDeletedOnIsNull(id).map(UserMapper::toUser);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userManagementRepository.save(new UserEntity(user));
        return UserMapper.toUser(userEntity);
    }
}
