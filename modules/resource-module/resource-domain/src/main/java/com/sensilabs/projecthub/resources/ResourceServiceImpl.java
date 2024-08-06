package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.cipher.DataEncryptionServiceImpl;
import com.sensilabs.projecthub.commons.ApplicationException;
import com.sensilabs.projecthub.commons.ErrorCode;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;
import com.sensilabs.projecthub.resources.model.ResourceType;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceAccess resourceAccess;
    private final DataEncryptionServiceImpl dataEncryptionService;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceAccess resourceAccess, DataEncryptionServiceImpl dataEncryptionService) {
        this.resourceRepository = resourceRepository;
        this.resourceAccess = resourceAccess;
        this.dataEncryptionService = dataEncryptionService;
    }

    @Override
    public Resource save(ResourceForm resourceForm, String createdById) throws AccessDeniedException {

        if (!resourceAccess.checkAccess(resourceForm.getProjectId(), resourceForm.getEnvironmentId(), createdById))
            throw new AccessDeniedException("Access denied to save the resource for user.");
        String encryptedValue = dataEncryptionService.encryptString(resourceForm.getValue());

        Instant now = Instant.now();
        Resource resource = Resource.builder()
                .id(UUID.randomUUID().toString())
                .name(resourceForm.getName())
                .description(resourceForm.getDescription())
                .value(encryptedValue)
                .resourceType(resourceForm.getType())
                .environmentId(resourceForm.getEnvironmentId())
                .projectId(resourceForm.getProjectId())
                .createdById(createdById)
                .createdOn(now)
                .lastModifiedOn(null)
                .deletedOn(null)
                .deletedById(null)
                .build();
        return resourceRepository.save(resource);


    }


    @Override
    public Optional<Resource> findById(String id) {
        return resourceRepository.findById(id)
                .map(resource -> {
                    String decryptedValue = dataEncryptionService.decryptString(resource.getValue());
                    resource.setValue(decryptedValue);
                    return resource;
                });
    }


    @Override
    public Resource update(UpdateResourceForm updateResourceForm) {
        String value = updateResourceForm.getValue();
        String encryptedValue = dataEncryptionService.encryptString(value);
        updateResourceForm.setValue(encryptedValue);
        return resourceRepository.update(updateResourceForm);
    }


    @Override
    public SearchResponse<Resource> search(SearchForm searchFrom) {

        SearchResponse<Resource> searchResponse = resourceRepository.search(searchFrom);
        searchResponse.getItems().forEach(resource -> {
            if (resource.getResourceType() == ResourceType.SECRET) {
                resource.setValue("*".repeat(resource.getValue().length()));
            }
            resource.setValue(dataEncryptionService.decryptString(resource.getValue()));
        });
        return searchResponse;
    }

    private Resource getOrThrow(String id) {
        return resourceRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public Resource delete(String id, String userId) {

        Resource existingResource = getOrThrow(id);
        existingResource.setDeletedById(userId);
        existingResource.setDeletedOn(Instant.now());

        return  resourceRepository.save(existingResource);
    }

}
