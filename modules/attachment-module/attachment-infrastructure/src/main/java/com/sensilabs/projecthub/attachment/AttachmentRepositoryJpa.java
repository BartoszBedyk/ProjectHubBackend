package com.sensilabs.projecthub.attachment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepositoryJpa extends JpaRepository<AttachmentEntity, String> {
}
