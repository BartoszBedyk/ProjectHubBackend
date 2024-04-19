package com.sensilabs.projecthub.login.pass.auth;

public interface PasswordEncoder {
    String encode(String rawText);
    boolean match(String rawText, String encoded);
}
