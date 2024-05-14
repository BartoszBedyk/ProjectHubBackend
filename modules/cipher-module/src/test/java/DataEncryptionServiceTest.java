import com.sensilabs.projecthub.cipher.CipherProps;
import com.sensilabs.projecthub.cipher.DataEncryptionService;
import com.sensilabs.projecthub.cipher.DataEncryptionServiceImpl;
import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DataEncryptionServiceTest {

    CipherProps props = new CipherPropsMock();
    DataEncryptionService service = new DataEncryptionServiceImpl(props);

    @Test
    void stringEncryptionTest() {
        String input = "Lorem ipsum dolor sit amet";

        String cipherText = service.encryptString(input);
        String rawText = service.decryptString(cipherText);

        assertEquals(input, rawText);
    }

    @Test
    void fileEncryptionTest() throws IOException {
        String inputFile = "input.txt";
        String encryptedFile = "encrypted.txt";

        byte[] inputBytes = readBytesFromFile(inputFile);
        byte[] encryptedBytes = encryptData(inputBytes);

        writeBytesToFile(encryptedBytes, encryptedFile);

        byte[] decryptedBytes = decryptData(encryptedFile);

        assertArrayEquals(inputBytes, decryptedBytes);
    }

    private byte[] readBytesFromFile(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[props.getBufferSize()];
            int bytesRead;
            while (true) {
                assert inputStream != null;
                if ((bytesRead = inputStream.read(buffer)) == -1) break;
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

    private void writeBytesToFile(byte[] bytes, String fileName) throws IOException {
        String outputPath = Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile();
        try (OutputStream outputStream = new FileOutputStream(outputPath)) {
            outputStream.write(bytes);
        }
    }

    private byte[] encryptData(byte[] data) {
        try {
            return service.encryptFile(new ByteArrayInputStream(data)).readAllBytes();
        } catch (IOException e) {
            throw new ApplicationException(ErrorCode.ENCRYPTION_FAILED);
        }
    }

    private byte[] decryptData(String fileName) {
        String outputPath = Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile();
        try {
            return service.decryptFile(new FileInputStream(outputPath)).readAllBytes();
        } catch (IOException e) {
            throw new ApplicationException(ErrorCode.DECRYPTION_FAILED);
        }
    }
}
