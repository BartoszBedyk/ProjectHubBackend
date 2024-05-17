package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class ResourceRepositoryAdapter implements ResourceRepository {

    private final ResourceRepositoryJpa repositoryJpa;

    public ResourceRepositoryAdapter(ResourceRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    public Resource save(Resource resource) {
        ResourceEntity entity = ResourceMapper.toResourceEntity(resource);
        repositoryJpa.save(entity);
        return ResourceMapper.toResource(entity);
    }

    @Override
    public List<Resource> findAll() {
        return repositoryJpa.findAll().stream().map(ResourceMapper::toResource).toList();
    }

    @Override
    public Optional<Resource> findById(String id) {
        return repositoryJpa.findById(id).map(ResourceMapper::toResource);
    }

    @Override
    public Resource update(UpdateResourceForm updateResourceForm) {
        Instant now = Instant.now();
        Resource resource = repositoryJpa.findById(updateResourceForm.getId()).map(ResourceMapper::toResource).orElse(null);
        resource.setName(updateResourceForm.getName());
        resource.setDescription(updateResourceForm.getDescription());
        resource.setValue(updateResourceForm.getValue());

        resource.setLastModifiedOn(now);
        return save(resource);
    }
}
