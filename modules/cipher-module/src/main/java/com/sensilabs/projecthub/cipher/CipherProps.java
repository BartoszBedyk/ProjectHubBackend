package com.sensilabs.projecthub.cipher;

public interface CipherProps {
    String getSecretKey();
    String getAlgorithmType();
    Integer getBufferSize();
}
