package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.activity.ActivityService;
import com.sensilabs.projecthub.activity.forms.DocumentOpenForm;
import com.sensilabs.projecthub.activity.forms.KeyOpenForm;
import com.sensilabs.projecthub.cipher.DataEncryptionServiceImpl;
import com.sensilabs.projecthub.commons.*;
import com.sensilabs.projecthub.project.ProjectService;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentService;
import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;
import com.sensilabs.projecthub.resources.model.ResourceType;
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
    private final DataEncryptionServiceImpl dataEncryptionService;
    private final LoggedUser loggedUser;
    private final ActivityService activityService;
    private final ProjectEnvironmentService projectEnvironmentService;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceAccess resourceAccess, DataEncryptionServiceImpl dataEncryptionService, LoggedUser loggedUser, ActivityService activityService, ProjectEnvironmentService projectEnvironmentService) {
        this.resourceRepository = resourceRepository;
        this.resourceAccess = resourceAccess;
        this.dataEncryptionService = dataEncryptionService;
        this.loggedUser = loggedUser;
        this.activityService = activityService;
        this.projectEnvironmentService = projectEnvironmentService;
    }

    @Override
    public Resource save(ResourceForm resourceForm, String createdById) throws AccessDeniedException {

        if (!resourceAccess.checkAccess(resourceForm.getProjectId(), resourceForm.getEnvironmentId(), createdById)){
            throw new  RuntimeException("Access denied to save the resource for user.");
        }

       String value = resourceForm.getValue();
       if(projectEnvironmentService.findById(resourceForm.getEnvironmentId()).isEncrypted()){
            value = dataEncryptionService.encryptString(resourceForm.getValue());
       }

        Instant now = Instant.now();
        Resource resource = Resource.builder()
                .id(UUID.randomUUID().toString())
                .name(resourceForm.getName())
                .description(resourceForm.getDescription())
                .value(value)
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
        Optional<Resource> resource = resourceRepository.findById(id);
        switch (resource.get().getResourceType()) {
            case SECRET -> {
                activityService.save(new KeyOpenForm(loggedUser.getUserId(), resource.get().getId()), loggedUser.getUserId());
            }
            case TEXT -> {
                activityService.save(new DocumentOpenForm(loggedUser.getUserId(), resource.get().getId()), loggedUser.getUserId());
            }
        }
        return resource
                .map(res -> {
                    if(projectEnvironmentService.findById(res.getEnvironmentId()).isEncrypted()) {
                        String decryptedValue = dataEncryptionService.decryptString(res.getValue());
                        res.setValue(decryptedValue);
                        return res;
                    }
                    return res;
                });
    }


    @Override
    public Resource update(UpdateResourceForm updateResourceForm) {
        String value = updateResourceForm.getValue();
        if(projectEnvironmentService.findById(resourceRepository.findById(updateResourceForm.getId()).get().getEnvironmentId()).isEncrypted()) {
            String encryptedValue = dataEncryptionService.encryptString(value);
            updateResourceForm.setValue(encryptedValue);
            return resourceRepository.update(updateResourceForm);

        }
        return resourceRepository.update(updateResourceForm);
    }


    @Override
    public SearchResponse<Resource> search(SearchForm searchFrom) {

        SearchResponse<Resource> searchResponse = resourceRepository.search(searchFrom);
        searchResponse.getItems().forEach(resource -> {
            if(projectEnvironmentService.findById(resource.getEnvironmentId()).isEncrypted()) {
                resource.setValue(dataEncryptionService.decryptString(resource.getValue()));
            }
            if (resource.getResourceType() == ResourceType.SECRET) {
                resource.setValue("*".repeat(resource.getValue().length()));
            }

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

    @Override
    public List<Resource> findByUser(String userID) {
         List<Resource> resources = resourceRepository.findByUser(userID);
         resources.forEach(resource -> {
             if(projectEnvironmentService.findById(resource.getEnvironmentId()).isEncrypted()) {
                 resource.setValue("*".repeat(resource.getValue().length()));
             }
             if (resource.getResourceType() == ResourceType.SECRET) {
                 resource.setValue("*".repeat(resource.getValue().length()));
             }
         });
         return resources;
    }

}
