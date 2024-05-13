package com.sensilabs.projecthub.cipher;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class DataEncryptionImpl implements DataEncryption {

    private final CipherProps props;
    public DataEncryptionImpl(CipherProps props) {
        this.props = props;
    }

    @Override
    public String encrypt(String rawText) {
        try {
            SecretKey secretKey = new SecretKeySpec(props.getSecretKey().getBytes(StandardCharsets.UTF_8), props.getAlgorithmType());
            Cipher cipher = Cipher.getInstance(props.getAlgorithmType());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(rawText.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.ENCRYPTION_FAILED);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        try {
            SecretKey secretKey = new SecretKeySpec(props.getSecretKey().getBytes(StandardCharsets.UTF_8), props.getAlgorithmType());
            Cipher cipher = Cipher.getInstance(props.getAlgorithmType());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.DECRYPTION_FAILED);
        }
    }
}
