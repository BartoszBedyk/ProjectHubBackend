package com.sensilabs.projecthub.attachment;

import java.io.InputStream;

public interface AttachmentService {
	Attachment save(InputStream inputStream, String originalFilename, String createdById);

	Attachment getById(String id, String createdById);
}
