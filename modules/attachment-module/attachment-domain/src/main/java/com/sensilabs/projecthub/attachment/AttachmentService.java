package com.sensilabs.projecthub;

import java.io.InputStream;

public interface AttachmentService {
	Attachment save(InputStream inputStream, String originalFilename, String createdById);

	Attachment getById(String id);
}
