package com.sensilabs.projecthub.resources;

import org.springframework.stereotype.Component;

@Component
public interface ResourceAccess {

    boolean checkAccess(String projectId, String environmentId, String userId);
}
