package com.sensilabs.projecthub.cipher;

import java.io.InputStream;

public interface DataEncryptionService {
    String encryptString(String rawText);
    String decryptString(String cipherText);
    InputStream encryptFile(InputStream inputStream);
    InputStream decryptFile(InputStream inputStream);
}
