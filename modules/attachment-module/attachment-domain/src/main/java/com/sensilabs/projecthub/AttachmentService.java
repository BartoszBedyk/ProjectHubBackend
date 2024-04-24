package com.sensilabs.projecthub;

import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Attachment save(MultipartFile file, String createdById);
    Attachment getById(String id);
}
