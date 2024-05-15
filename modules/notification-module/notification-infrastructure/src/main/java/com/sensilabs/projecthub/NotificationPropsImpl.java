package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.NotificationProps;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class NotificationPropsImpl implements NotificationProps {
    final private Environment env;

    public NotificationPropsImpl(Environment env) {
        this.env = env;
    }

    @Override
    public int nextMailAttemptDelayInSeconds() {
        return env.getRequiredProperty("app.notification.nextMailAttemptDelayInSeconds", Integer.class);
    }

    @Override
    public int numberOfAttempts() {
        return env.getRequiredProperty("app.notification.numberOfAttempts", Integer.class);
    }

    @Override
    public int numberOfThreadsAndMailPerThread() {
        return env.getRequiredProperty("app.notification.numberOfThreadsAndMailPerThread", Integer.class);
    }


}
