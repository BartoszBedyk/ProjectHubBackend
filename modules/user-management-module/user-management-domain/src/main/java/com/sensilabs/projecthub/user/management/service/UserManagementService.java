package com.sensilabs.projecthub.user.management.service;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.forms.EditUserForm;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserManagementService {

        SearchResponse<User> search(SearchForm form);
        User get(String id);
        User save(@Valid CreateUserForm userRequest);
        User saveSysAdminOnStartup(@Valid CreateUserForm userRequest);
        User update(@Valid EditUserForm userRequest);

        User block(String id);
        User unBlock(String id);
        void delete(String id);

}
