package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
    Resource save(Resource resource);
    List<Resource> findAll();
    Optional<Resource> findById(String id);
    Resource update(UpdateResourceForm resourceForm);
}
