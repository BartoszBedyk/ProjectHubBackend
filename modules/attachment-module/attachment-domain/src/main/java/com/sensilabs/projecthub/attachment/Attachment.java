package com.sensilabs.projecthub.attachment;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {
    private String id;
    private String name;
    private String path;
    private Instant createdOn;
    private String createdById;
}
