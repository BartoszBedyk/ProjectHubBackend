package com.sensilabs.projecthub.login.pass.auth.security;

import com.sensilabs.projecthub.login.pass.auth.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderAdapter implements PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoderAdapter(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String encode(String rawText) {
        return bCryptPasswordEncoder.encode(rawText);
    }

    @Override
    public boolean match(String rawText, String encoded) {
        return bCryptPasswordEncoder.matches(rawText, encoded);
    }
}
