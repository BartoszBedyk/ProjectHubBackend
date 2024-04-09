package com.sensilabs.projecthub.commons;

public enum ErrorCode {
    USER_NOT_FOUND("User not found");

    ErrorCode(String message) {
        this.message = message;
    }

    final String message;


}
