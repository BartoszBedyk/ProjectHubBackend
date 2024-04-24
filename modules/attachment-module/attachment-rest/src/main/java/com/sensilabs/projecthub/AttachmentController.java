package com.sensilabs.projecthub;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final StorageService storageService;


    public AttachmentController(AttachmentService attachmentService, StorageService storageService) {
        this.attachmentService = attachmentService;
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public Attachment upload(@RequestParam("file") MultipartFile file,
                                        @RequestParam("createdById") String createdById) {
            return attachmentService.save(file, createdById);
    }

    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<?> download(@PathVariable String attachmentId) {
        Attachment attachment = attachmentService.getById(attachmentId);
        byte[] fileData = storageService.get(attachment.getPath());

            ByteArrayResource resource = new ByteArrayResource(fileData);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                    .body(resource);
    }
}
