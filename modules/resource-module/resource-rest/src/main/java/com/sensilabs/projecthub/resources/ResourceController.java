package com.sensilabs.projecthub.resources;

import com.sensilabs.projecthub.resources.forms.*;
import com.sensilabs.projecthub.resources.model.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("create-attachment-resource")
    public Resource createAttachmentResource(@RequestBody CreateAttachmentResourceForm form) {
        String environmentId = UUID.randomUUID().toString();
        String projectId = UUID.randomUUID().toString();
        String createdById = UUID.randomUUID().toString();

        return resourceService.save(form, environmentId, projectId, createdById);
    }

    @PostMapping("create-text-resource")
    public Resource createTextResource(@RequestBody CreateTextResourceForm form) {
        String environmentId = UUID.randomUUID().toString();
        String projectId = UUID.randomUUID().toString();
        String createdById = UUID.randomUUID().toString();

        return resourceService.save(form, environmentId, projectId, createdById);
    }

    @PostMapping("create-link-resource")
    public Resource createLinkResource(@RequestBody CreateLinkResourceForm form) {
        String environmentId = UUID.randomUUID().toString();
        String projectId = UUID.randomUUID().toString();
        String createdById = UUID.randomUUID().toString();

        return resourceService.save(form, environmentId, projectId, createdById);
    }

    @PostMapping("create-secret-resource")
    public Resource createSecretResource(@RequestBody CreateSecretResourceForm form) {
        String environmentId = UUID.randomUUID().toString();
        String projectId = UUID.randomUUID().toString();
        String createdById = UUID.randomUUID().toString();

        return resourceService.save(form, environmentId, projectId, createdById);
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

}
