package com.sensilabs.projecthub.user.management.service;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.User;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.forms.EditUserForm;

public interface UserManagementService {

        SearchResponse<User> search(SearchForm form);
        User get(String id);
        User save(CreateUserForm userRequest);
        User update(EditUserForm userRequest);

        User block(String id);
        User unBlock(String id);
        void delete(String id);

}
