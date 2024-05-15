package com.sensilabs.projecthub.login.pass.auth.security;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.security.JwtAuthUser;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserManagementRepository repository;


    public CustomUserDetailsService(UserManagementRepository repository) {
        this.repository = repository;
    }

    private User getOrThrow(String id) {
        return repository.getNotDeleted(id).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = getOrThrow(id);
        return JwtAuthUser.builder()
                .id(user.getId())
                .locked(user.isBlocked())
                .build();
    }
}

