package com.sensilabs.projecthub.login.pass.auth;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Objects;

@Component
public class PasswordEncoderAdapter implements PasswordEncoder {

    // TODO correct implementation
    @Override
    public String encode(String rawText) {
        return rawText;
    }

    // TODO correct implementation
    @Override
    public boolean match(String rawText, String encoded) {
        return Objects.equals(rawText, encoded);
    }
}
