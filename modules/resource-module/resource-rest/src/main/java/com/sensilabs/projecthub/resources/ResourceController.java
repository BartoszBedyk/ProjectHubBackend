package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.commons.*;
import com.sensilabs.projecthub.resources.forms.*;
import com.sensilabs.projecthub.resources.model.Resource;
import com.sensilabs.projecthub.resources.model.ResourceType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.join;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    private final ResourceService resourceService;
    private final LoggedUser loggedUser;
    private final ResourceAccess resourceAccess;

    public ResourceController(ResourceService resourceService, LoggedUser loggedUser, ResourceAccess resourceAccess) {
        this.resourceService = resourceService;
        this.loggedUser = loggedUser;
        this.resourceAccess = resourceAccess;
    }

    @PostMapping("attachment")
    public Resource createAttachmentResource(@RequestBody CreateAttachmentResourceForm form) throws AccessDeniedException {
        return resourceService.save(form, loggedUser.getUserId());
    }

    @PostMapping("text")
    public Resource createTextResource(@RequestBody CreateTextResourceForm form) throws AccessDeniedException {
        return resourceService.save(form, loggedUser.getUserId());
    }

    @PostMapping("link")
    public Resource createLinkResource(@RequestBody CreateLinkResourceForm form) throws AccessDeniedException {
        return resourceService.save(form, loggedUser.getUserId());
    }

    @PostMapping("secret")
    public Resource createSecretResource(@RequestBody CreateSecretResourceForm form) throws AccessDeniedException {
        return resourceService.save(form, loggedUser.getUserId());
    }

    @GetMapping("{id}")
    public Resource getResourceById(@PathVariable("id") String id) {
        return resourceService.findById(id).orElse(null);
    }

    @PutMapping("/update")
    public Resource updateResource(@RequestBody UpdateResourceForm form) {
        return resourceService.update(form);
    }


    @PostMapping("/search")
    public SearchResponse<Resource> search(@RequestBody SearchForm searchForm) {
        return resourceService.search(searchForm);
    }



    @PostMapping("{projectId}/{environmentId}/search")
    public SearchResponse<Resource> search(@RequestBody SearchForm searchForm, @PathVariable String projectId, @PathVariable String environmentId){
        resourceAccess.checkAccess(projectId, environmentId, loggedUser.getUserId());
        searchForm.getCriteria().clear();
        List<SearchFormCriteria> criteria = new java.util.ArrayList<>(List.of());
        criteria.add(new SearchFormCriteria("projectId", projectId, CriteriaOperator.EQUALS));
        criteria.add(new SearchFormCriteria("environmentId", environmentId, CriteriaOperator.EQUALS));
        searchForm.setCriteria(criteria);
        return resourceService.search(searchForm);
    }

    @PostMapping("/secret-unmasked/{id}")
    public String readSecret(@PathVariable String id) {
        return resourceService.findById(id).get().getValue();
    }

}
