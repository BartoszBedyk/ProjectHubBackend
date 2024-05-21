package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceAccess resourceAccess;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceAccess resourceAccess) {
        this.resourceRepository = resourceRepository;
        this.resourceAccess = resourceAccess;
    }

    @Override
    public Resource save(ResourceForm resourceForm, String createdById) throws AccessDeniedException {
        String userId = UUID.randomUUID().toString();   // zastąpić id z modułu Kacpra
        if( resourceAccess.checkAccess(resourceForm.getProjectId(), resourceForm.getEnvironmentId(), userId)){
            Instant now = Instant.now();
            Resource resource =  Resource.builder()
                    .id(UUID.randomUUID().toString())
                    .name(resourceForm.getName())
                    .description(resourceForm.getDescription())
                    .value(resourceForm.getValue())
                    .resourceType(resourceForm.getType())
                    .environmentId(resourceForm.getEnvironmentId())
                    .projectId(resourceForm.getProjectId())
                    .createdById(createdById)
                    .createdOn(now)
                    .lastModifiedOn(null)
                    .build();
            return resourceRepository.save(resource);
        }
        else{
            throw new AccessDeniedException("Access denied to save the resource for user.");
        }


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

    @Override
    public SearchResponse<Resource> search(SearchForm searchFrom) {
        return resourceRepository.search(searchFrom);
    }
}
