package com.sensilabs.projecthub.attachment;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

import com.sensilabs.projecthub.activity.ActivityService;
import com.sensilabs.projecthub.activity.forms.DocumentDownloadForm;
import org.springframework.stereotype.Service;

import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	private final Path root = Paths.get("uploads");
	private final AttachmentRepository attachmentRepository;
	private final StorageService storageService;
	private final ActivityService activityService;

	public AttachmentServiceImpl(AttachmentRepository attachmentRepository, StorageService storageService, ActivityService activityService) {
		this.attachmentRepository = attachmentRepository;
		this.storageService = storageService;
        this.activityService = activityService;
    }

	@Override
	public Attachment save(InputStream file, String originalFilename, String createdById) {
		try {
			String path = storageService.save(file);
			Attachment attachment = Attachment.builder()
					.id(UUID.randomUUID().toString())
					.name(originalFilename)
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
	public Attachment getById(String id, String createdById) {
		activityService.save(new DocumentDownloadForm(createdById, id), createdById);
		return attachmentRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.ATTACHMENT_NOT_FOUND));
	}
}
