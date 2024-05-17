package com.sensilabs.projecthub.notification.model;

import lombok.Getter;

@Getter
public enum NotificationType {
    ACCOUNT_CREATE("Account create", "create-account-template"),
    PASSWORD_RESET("Reset password", "reset-password-template");

    final String subject;
    final String templateId;

    NotificationType(String subject, String templateId) {
        this.subject = subject;
        this.templateId = templateId;
    }
}
