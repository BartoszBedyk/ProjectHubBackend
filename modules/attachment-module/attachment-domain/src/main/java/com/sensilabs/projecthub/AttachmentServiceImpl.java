package com.sensilabs.projecthub;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements  AttachmentService{
    private final Path root = Paths.get("uploads");
    private final AttachmentRepository attachmentRepository;
    private final StorageService storageService;


    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, StorageService storageService) {
        this.attachmentRepository = attachmentRepository;
        this.storageService = storageService;
    }

    @Override
    public Attachment save(MultipartFile file, String createdById) {
        String fileName = file.getOriginalFilename();
        try {
            String path = storageService.save(file);
            Attachment attachment = Attachment.builder()
                    .id(UUID.randomUUID().toString())
                    .name(fileName)
                    .path(path)
                    .createdById(createdById)
                    .createdOn(Instant.now())
                    .build();
            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save attachment: " + e.getMessage(), e);
        }
    }


    @Override
    public Attachment getById(String id) {
        return attachmentRepository.findById(id).orElseThrow(()->new ApplicationException(ErrorCode.ATTATCHMENT_NOT_FOUND));
    }
}
