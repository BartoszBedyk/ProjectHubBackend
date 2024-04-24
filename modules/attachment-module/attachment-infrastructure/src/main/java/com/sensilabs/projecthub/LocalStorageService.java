package com.sensilabs.projecthub;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class LocalStorageService implements StorageService{
    @Override
    public String save(MultipartFile file) {
        String path = preparePath() + "/" + file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(), Paths.get(path));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
        return path;
    }

    @Override
    public byte[] get(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
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
