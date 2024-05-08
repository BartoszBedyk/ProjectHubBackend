package com.sensilabs.projecthub.attachment;

import org.hibernate.cfg.Environment;

public class AttachmentPropsImpl implements AttachmentProps{
    private final Environment env;

    public AttachmentPropsImpl(Environment env) {
        this.env = env;
    }
}
