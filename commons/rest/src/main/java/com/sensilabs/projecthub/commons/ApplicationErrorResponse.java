package com.sensilabs.projecthub.commons;

import java.time.Instant;
import java.util.Map;

public class ApplicationErrorResponse {
    private Instant timestamp;
    private int status;
    private ErrorCode errorCode;
    private String message;
    private Map<String, String> errors;
}
