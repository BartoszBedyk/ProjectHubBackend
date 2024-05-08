package com.sensilabs.projecthub.activity;

import org.hibernate.cfg.Environment;

public class ActivityPropsImpl implements ActivityProps{
    private final Environment env;

    public ActivityPropsImpl(Environment env) {
        this.env = env;
    }
}
