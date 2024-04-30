package com.sensilabs.projecthub.commons;

public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    WRONG_PASSWORD("Wrong password"),
    LINK_EXPIRED("Link expired"),
    VALIDATION_EXCEPTION("Invalid data provided"),
    UNKNOWN_ERROR("Something went wrong"),
    UNSUPPORTED_OPERATOR("Unsupported operator"),
    ATTATCHMENT_NOT_FOUND("Attachment not found"),
    FILE_NOT_FOUND("File not found"),
    PROJECT_NOT_FOUND("Project not found");

    ErrorCode(String message) {
        this.message = message;
    }

    final String message;
}
