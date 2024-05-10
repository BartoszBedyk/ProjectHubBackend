package com.sensilabs.projecthub.user.management;

import com.sensilabs.projecthub.commons.LoggedUser;

public class LoggedUserMock implements LoggedUser {
    @Override
    public String getUserId() {
        return "user_id";
    }
}
