package com.sensilabs.projecthub.commons;

public class ApplicationException extends RuntimeException {
    private final ErrorCode code;

    public ApplicationException(ErrorCode code) {
        super(code.message);
        this.code = code;
    }
}
