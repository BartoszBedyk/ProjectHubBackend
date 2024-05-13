package com.sensilabs.projecthub.cipher;

public interface DataEncryption {
    String encrypt(String rawText);
    String decrypt(String cipherText);
}
