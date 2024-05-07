package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.ProjectProps;
import org.springframework.core.env.Environment;

public class ProjectPropsImpl implements ProjectProps {
    final private Environment env;

    public ProjectPropsImpl(Environment env) {
        this.env = env;
    }

    @Override
    public int delay() {
        return env.getRequiredProperty("app.notification.delay", Integer.class);
    }

    @Override
    public int numberOfAttempts() {
        return env.getRequiredProperty("app.notification.numberOfAttempts", Integer.class);
    }
}
