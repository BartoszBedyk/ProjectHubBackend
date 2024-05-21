package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.resources.forms.*;
import com.sensilabs.projecthub.resources.model.Resource;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("attachment")
    public Resource createAttachmentResource(@RequestBody CreateAttachmentResourceForm form) throws AccessDeniedException {
        String createdById = UUID.randomUUID().toString();
        return resourceService.save(form, createdById);
    }

    @PostMapping("text")
    public Resource createTextResource(@RequestBody CreateTextResourceForm form) throws AccessDeniedException {
        String createdById = UUID.randomUUID().toString();
        return resourceService.save(form, createdById);
    }

    @PostMapping("link")
    public Resource createLinkResource(@RequestBody CreateLinkResourceForm form) throws AccessDeniedException {
        String createdById = UUID.randomUUID().toString();

        return resourceService.save(form, createdById);
    }

    @PostMapping("secret")
    public Resource createSecretResource(@RequestBody CreateSecretResourceForm form) throws AccessDeniedException {
        String createdById = UUID.randomUUID().toString();
        return resourceService.save(form, createdById);
    }

    @GetMapping
    public List<Resource> getAllResources() {
        return resourceService.findAll();
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

}
