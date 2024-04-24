package com.sensilabs.projecthub.commons;

public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    VALIDATION_EXCEPTION("Invalid data provided"),
    UNKNOWN_ERROR("Something went wrong"),
    UNSUPPORTED_OPERATOR("Unsupported operator"),
    ATTATCHMENT_NOT_FOUND("Attachment not found"),
    FILE_NOT_FOUND("File not found");

    ErrorCode(String message) {
        this.message = message;
    }

    final String message;
}
