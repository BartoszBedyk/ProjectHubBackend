package com.sensilabs.projecthub.cipher;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class DataEncryptionServiceImpl implements DataEncryptionService {

    private final CipherProps props;
    public DataEncryptionServiceImpl(CipherProps props) {
        this.props = props;
    }

    @Override
    public String encryptString(String rawText) {
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
    public String decryptString(String cipherText) {
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

    @Override
    public InputStream encryptFile(InputStream inputStream) {
        try {
            SecretKey secretKey = new SecretKeySpec(props.getSecretKey().getBytes(StandardCharsets.UTF_8), props.getAlgorithmType());
            Cipher cipher = Cipher.getInstance(props.getAlgorithmType());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            processStream(inputStream, outputStream, cipher);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.ENCRYPTION_FAILED);
        }
    }

    @Override
    public InputStream decryptFile(InputStream inputStream) {
        try {
            SecretKey secretKey = new SecretKeySpec(props.getSecretKey().getBytes(StandardCharsets.UTF_8), props.getAlgorithmType());
            Cipher cipher = Cipher.getInstance(props.getAlgorithmType());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            processStream(inputStream, outputStream, cipher);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.DECRYPTION_FAILED);
        }
    }

    private void processStream(InputStream inputStream, ByteArrayOutputStream outputStream, Cipher cipher) {
        byte[] buffer = new byte[props.getBufferSize()];
        int bytesRead;

        try (inputStream; outputStream) {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] transformedBytes = cipher.update(buffer, 0, bytesRead);
                if (transformedBytes != null) {
                    outputStream.write(transformedBytes);
                }
            }
            byte[] finalBytes = cipher.doFinal();
            if (finalBytes != null) {
                outputStream.write(finalBytes);
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.FILE_STREAM_PROCESSING_FAILED);
        }
    }
}
