package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public Project create(@RequestBody CreateProjectForm form) {
        return projectService.save(form, form.getUserId());
    }

    @PutMapping("/update")
    public Project update(@RequestBody UpdateProjectForm form) {
        return projectService.update(form);
    }

    @GetMapping("/get/{id}")
    public Project get(@PathVariable("id") String id) {
        return projectService.getById(id);
    }

    @PostMapping("/search")
    public SearchResponse<Project> search(@RequestBody SearchForm searchForm) {
        return projectService.search(searchForm);
    }
}
