package com.sensilabs.projecthub.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityUserService {
    UserDetails getUserById(String id);
}
