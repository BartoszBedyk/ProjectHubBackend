package com.sensilabs.projecthub.project;

import org.hibernate.cfg.Environment;

public class ProjectPropsImpl implements ProjectProps{
    private final Environment env;

    public ProjectPropsImpl(Environment env) {
        this.env = env;
    }
}
