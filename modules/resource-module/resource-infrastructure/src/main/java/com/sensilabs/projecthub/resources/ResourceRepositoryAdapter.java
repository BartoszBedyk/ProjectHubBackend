package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.resources.forms.ResourceForm;
import com.sensilabs.projecthub.resources.forms.UpdateResourceForm;
import com.sensilabs.projecthub.resources.model.Resource;
import com.sensilabs.projecthub.utils.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return List.of();
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

    @Override
    public SearchResponse search(SearchForm searchForm) {
        Specification<ResourceEntity> specification = SearchSpecification.buildSpecification(searchForm.getCriteria());
        Page<ResourceEntity> resourceEntityPage = repositoryJpa.findAll(specification, SearchSpecification.getPageRequest(searchForm));
        return SearchResponse.<Resource>builder()
                .items(resourceEntityPage.getContent().stream()
                        .map(ResourceMapper::toResource)
                        .collect(Collectors.toList()))
                .total(resourceEntityPage.getTotalElements())
                .build();
    }

    @Override
    public Boolean checkAccess(String projectId, String environmentId, String userId) {
        return null;
    }


}
