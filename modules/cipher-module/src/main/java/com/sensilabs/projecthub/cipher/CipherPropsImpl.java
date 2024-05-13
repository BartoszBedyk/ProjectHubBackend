package com.sensilabs.projecthub.cipher;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CipherPropsImpl implements CipherProps {

    private final Environment env;

    public CipherPropsImpl(Environment env) {
        this.env = env;
    }

    @Override
    public String getSecretKey() {
        return env.getRequiredProperty("app.cipher.secret");
    }

    @Override
    public String getAlgorithmType() {
        return env.getRequiredProperty("app.cipher.algorithm");
    }

    @Override
    public Integer getBufferSize() {
        return Integer.parseInt(env.getRequiredProperty("app.cipher.bufferSize"));
    }
}
