package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
    Resource save(Resource resource);

    List<Resource> findAll();

    Optional<Resource> findById(String id);
    Resource update(UpdateResourceForm resourceForm);
    SearchResponse search(SearchForm searchForm);

    Boolean checkAccess(String projectId, String environmentId, String userId);
    List<Resource> findByUser(String userID);
}
