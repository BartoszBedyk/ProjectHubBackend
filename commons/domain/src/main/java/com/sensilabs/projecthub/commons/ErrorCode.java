package com.sensilabs.projecthub.commons;

public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    WRONG_PASSWORD("Wrong password"),
    LINK_EXPIRED("Link expired"),
    VALIDATION_EXCEPTION("Invalid data provided"),
    UNKNOWN_ERROR("Something went wrong"),
    UNSUPPORTED_OPERATOR("Unsupported operator");

    ErrorCode(String message) {
        this.message = message;
    }

    final String message;
}
