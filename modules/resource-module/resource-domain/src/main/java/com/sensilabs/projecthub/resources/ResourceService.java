package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Validated
public interface ResourceService {
    Resource save(@Valid ResourceForm resourceForm, String createdById) throws AccessDeniedException;
    Optional<Resource> findById(String id);
    Resource update(@Validated UpdateResourceForm updateResourceForm, String loggedUserId);
    SearchResponse<Resource> search(SearchForm searchFrom);
    Resource delete(String id, String userID);
}
