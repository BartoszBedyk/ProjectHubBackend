package com.sensilabs.projecthub.attachment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import com.sensilabs.projecthub.cipher.DataEncryptionService;
import org.springframework.stereotype.Component;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;

@Component
public class LocalStorageService implements StorageService {

	private final DataEncryptionService dataEncryptionService;

    public LocalStorageService(DataEncryptionService dataEncryptionService) {
        this.dataEncryptionService = dataEncryptionService;
    }

    @Override
	public String save(InputStream inputStream) {
		String path = preparePath() + "/" + UUID.randomUUID();
		try (InputStream encryptedInput = dataEncryptionService.encryptFile(inputStream)) {
			Files.copy(encryptedInput, Paths.get(path));
		} catch (FileAlreadyExistsException e) {
			throw new RuntimeException("A file of that name already exists: " + path, e);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save file to path: " + path, e);
		}
		return path;
	}

	@Override
	public byte[] get(String path) {
		try {
			byte[] data = Files.readAllBytes(Paths.get(path));
			InputStream inputStream = new ByteArrayInputStream(data);
			InputStream decryptedStream = dataEncryptionService.decryptFile(inputStream);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = decryptedStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new ApplicationException(ErrorCode.FILE_NOT_FOUND);
		}
	}

	public String preparePath() {
		String basePath = "/attachments";
		LocalDate currentDate = LocalDate.now();
		String id = UUID.randomUUID().toString();
		String folderPath = basePath + "/" + currentDate + "/" + id;
		try {
			Path baseFolderPath = Paths.get(basePath);
			if (!Files.exists(baseFolderPath)) {
				Files.createDirectories(baseFolderPath);
			}
			Path dateFolderPath = Paths.get(basePath, String.valueOf(currentDate));
			if (!Files.exists(dateFolderPath)) {
				Files.createDirectories(dateFolderPath);
			}
			Path idFolderPath = Paths.get(dateFolderPath.toString(), id);
			if (!Files.exists(idFolderPath)) {
				Files.createDirectories(idFolderPath);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to create directory or save file", e);
		}
		return folderPath;
	}
}
