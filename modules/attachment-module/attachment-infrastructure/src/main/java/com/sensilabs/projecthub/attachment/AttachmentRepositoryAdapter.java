package com.sensilabs.projecthub.attachment;

import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class AttachmentRepositoryAdapter implements AttachmentRepository{
    private final AttachmentRepositoryJpa attachmentRepositoryJpa;

    public AttachmentRepositoryAdapter(AttachmentRepositoryJpa attachmentRepositoryJpa) {
        this.attachmentRepositoryJpa = attachmentRepositoryJpa;
    }

    @Override
    public Attachment save(Attachment attachment) {
        AttachmentEntity attachmentEntity = AttachmentMapper.toAttachmentEntity(attachment);
        attachmentRepositoryJpa.save(attachmentEntity);
        return AttachmentMapper.toAttachment(attachmentEntity);
    }

    @Override
    public Optional<Attachment> findById(String id) {
        return attachmentRepositoryJpa.findById(id).map(AttachmentMapper::toAttachment);
    }
}
