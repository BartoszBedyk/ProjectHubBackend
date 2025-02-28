package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.LoggedUser;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final LoggedUser loggedUser;

    public ProjectController(ProjectService projectService, LoggedUser loggedUser) {
        this.projectService = projectService;
        this.loggedUser = loggedUser;
    }

    @PostMapping("/create")
    public Project create(@RequestBody CreateProjectForm form) {
        return projectService.save(form, loggedUser.getUserId());
    }

    @PutMapping("/update")
    public Project update(@RequestBody UpdateProjectForm form) {
        return projectService.update(form, loggedUser.getUserId());
    }

    @GetMapping("/get/{id}")
    public Project get(@PathVariable("id") String id) {
        return projectService.getById(id);
    }

    @PostMapping("/search")
    public SearchResponse<Project> search(@RequestBody SearchForm searchForm) {
        return projectService.search(searchForm, loggedUser.getUserId());
    }

    @DeleteMapping("/delete/{id}")
    public Project delete(@PathVariable("id") String id) {
        return projectService.delete(id, loggedUser.getUserId());
    }
}
