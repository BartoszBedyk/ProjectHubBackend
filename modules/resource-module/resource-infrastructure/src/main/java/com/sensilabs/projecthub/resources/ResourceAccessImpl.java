package com.sensilabs.projecthub.resources;


import org.springframework.stereotype.Service;

@Service
public class ResourceAccessImpl implements ResourceAccess {

    private final ResourceRepository resourceRepository;

    public ResourceAccessImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public boolean checkAccess(String projectId, String environmentId, String userId) {
        return true;
    }
}
