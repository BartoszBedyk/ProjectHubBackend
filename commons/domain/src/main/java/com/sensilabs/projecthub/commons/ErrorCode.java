package com.sensilabs.projecthub.commons;

public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    USER_NOT_LOGGED_IN("User is not logged in"),
    WRONG_LOGIN_OR_PASSWORD("Wrong login or password"),
    LINK_EXPIRED("Link expired"),
    VALIDATION_EXCEPTION("Invalid data provided"),
    UNKNOWN_ERROR("Something went wrong"),
    UNSUPPORTED_OPERATOR("Unsupported operator"),
    ATTACHMENT_NOT_FOUND("Attachment not found"),
    FILE_NOT_FOUND("File not found"),
    PROJECT_NOT_FOUND("Project not found"),
    ENCRYPTION_FAILED("Encryption failed"),
    DECRYPTION_FAILED("Decryption failed"),
    FILE_STREAM_PROCESSING_FAILED("File stream processing failed"),
    PROJECT_MEMBER_NOT_FOUND("Project member not found"),
    PROJECT_ENVIRONMENT_NOT_FOUND("Project environment not found"),
    NOT_PROJECT_OWNER("You are not project owner"),
    TECHNOLOGY_NOT_FOUND("Technology not found");

    ErrorCode(String message) {
        this.message = message;
    }

    final String message;
}
