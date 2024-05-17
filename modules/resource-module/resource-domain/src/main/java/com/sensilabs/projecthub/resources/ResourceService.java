package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
public interface ResourceService {
    Resource save(@Valid ResourceForm resourceForm, String environmentId, String projectId, String createdById);
    List<Resource> findAll();
    Optional<Resource> findById(String id);
    Resource update(@Validated UpdateResourceForm updateResourceForm);
}
