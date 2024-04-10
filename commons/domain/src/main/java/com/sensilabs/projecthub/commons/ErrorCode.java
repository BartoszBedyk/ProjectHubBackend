package com.sensilabs.projecthub.commons;

public enum ErrorCode {
    USER_NOT_FOUND("User not found", 404),
    VALIDATION_EXCEPTION("Invalid data provided", 400);

    ErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    final String message;
    final int status;
}
