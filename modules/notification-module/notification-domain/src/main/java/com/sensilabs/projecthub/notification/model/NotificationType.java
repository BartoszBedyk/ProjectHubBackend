package com.sensilabs.projecthub.notification.model;

import lombok.Getter;

@Getter
public enum NotificationType {
    ACCOUNT_CREATE("Account create", "createAccountTemplate"),
    PASSWORD_RESET("Reset password", "resetPasswordTemplate");
    //ITD

    final String subject;
    final String templateId;

    NotificationType(String subject, String templateId) {
        this.subject = subject;
        this.templateId = templateId;
    }
}
