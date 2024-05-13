package com.sensilabs.projecthub.cipher;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CipherProps {

    private final Environment env;

    public CipherProps(Environment env) {
        this.env = env;
    }

    public String getSecretKey() {
        return env.getRequiredProperty("app.cipher.secret");
    }

    public String getAlgorithmType() {
        return env.getRequiredProperty("app.cipher.algorithm");
    }
}
