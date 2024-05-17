package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Resource save(ResourceForm resourceForm,  String environmentId, String projectId, String createdById) {
        Instant now = Instant.now();
        Resource resource =  Resource.builder()
                .id(UUID.randomUUID().toString())
                .name(resourceForm.getName())
                .description(resourceForm.getDescription())
                .value(resourceForm.getValue())
                .resourceType(resourceForm.getType())
                .environmentId(environmentId)
                .projectId(projectId)
                .createdById(createdById)
                .createdOn(now)
                .lastModifiedOn(null)
                .build();
        return resourceRepository.save(resource);
    }

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Override
    public Optional<Resource> findById(String id) {
        return resourceRepository.findById(id);
    }

    @Override
    public Resource update(UpdateResourceForm updateResourceForm) {
        return resourceRepository.update(updateResourceForm);
    }
}
