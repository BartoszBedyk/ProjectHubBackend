package com.sensilabs.projecthub.attachment;

public class AttachmentMapper {

    public static AttachmentEntity toAttachmentEntity(Attachment attachment) {
        return AttachmentEntity.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .path(attachment.getPath())
                .createdById(attachment.getCreatedById())
                .createdOn(attachment.getCreatedOn())
                .build();
    }

    public static Attachment toAttachment(AttachmentEntity attachmentEntity) {
        return Attachment.builder()
                .id(attachmentEntity.getId())
                .name(attachmentEntity.getName())
                .path(attachmentEntity.getPath())
                .createdById(attachmentEntity.getCreatedById())
                .createdOn(attachmentEntity.getCreatedOn())
                .build();
    }
}
