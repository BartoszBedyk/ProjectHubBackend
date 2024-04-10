package com.sensilabs.projecthub.commons;

public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    VALIDATION_EXCEPTION("Invalid data provided");

    ErrorCode(String message) {
        this.message = message;
    }

    final String message;
}
