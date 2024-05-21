package com.sensilabs.projecthub.resources;


import org.springframework.stereotype.Service;

@Service
public class ResourceAccessImpl implements ResourceAccess {
    @Override
    public boolean checkAccess(String projectId, String environmentId, String userId) {
        return true;
    }
}
