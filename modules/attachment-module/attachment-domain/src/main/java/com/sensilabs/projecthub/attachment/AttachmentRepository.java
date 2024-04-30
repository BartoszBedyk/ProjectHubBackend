package com.sensilabs.projecthub;

import java.util.Optional;

public interface AttachmentRepository {
    Attachment save(Attachment attachment);
    Optional<Attachment> findById(String id);
}
