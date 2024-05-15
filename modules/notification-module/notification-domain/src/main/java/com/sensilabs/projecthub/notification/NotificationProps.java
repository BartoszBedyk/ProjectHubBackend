package com.sensilabs.projecthub.notification;


public interface NotificationProps {
    int nextMailAttemptDelayInSeconds();
    int numberOfAttempts();
    int numberOfThreadsAndMailPerThread();

}
