package com.sensilabs.projecthub.attachment;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	public Attachment upload(@RequestParam("file") MultipartFile file, @RequestParam("createdById") String createdById) throws IOException {
		try (InputStream in = file.getInputStream()) {
			return attachmentService.save(in, file.getOriginalFilename(), createdById);
		}
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
