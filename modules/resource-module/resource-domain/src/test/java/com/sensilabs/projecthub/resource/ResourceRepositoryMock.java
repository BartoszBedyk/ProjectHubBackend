package com.sensilabs.projecthub.resource;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.resources.ResourceRepository;
import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ResourceRepositoryMock implements ResourceRepository {

    Map<String, Resource> mockDB = new HashMap<>();

    @Override
    public Resource save(Resource resource) {
        mockDB.put(resource.getId(), resource);
        return resource;
    }

    @Override
    public List<Resource> findAll() {
        return null;
    }

    @Override
    public Optional<Resource> findById(String id) {
        return Optional.ofNullable(mockDB.get(id));
    }

    @Override
    public Resource update(UpdateResourceForm resourceForm) {
        Instant now = Instant.now();
        Resource resource = mockDB.get(resourceForm.getId());
        resource.setName(resourceForm.getName());
        resource.setDescription(resourceForm.getDescription());
        resource.setLastModifiedOn(now);
        resource.setValue(resourceForm.getValue());
       return resource;
    }

    @Override
    public SearchResponse search(SearchForm searchForm) {
        return null;
    }
}
