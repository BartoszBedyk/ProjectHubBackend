package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.LoggedUser;
import com.sensilabs.projecthub.project.environment.ProjectEnvironment;
import com.sensilabs.projecthub.project.environment.forms.CreateProjectEnvironmentForm;
import com.sensilabs.projecthub.project.environment.forms.UpdateProjectEnvironmentForm;
import com.sensilabs.projecthub.project.environment.service.ProjectEnvironmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project-environment")
public class ProjectEnvironmentController {
    private final ProjectEnvironmentService service;
    private final LoggedUser loggedUser;

    public ProjectEnvironmentController(ProjectEnvironmentService service, LoggedUser loggedUser) {
        this.service = service;
        this.loggedUser = loggedUser;
    }

    @PostMapping("/save")
    public void create(@RequestBody CreateProjectEnvironmentForm form) {
        service.create(form, loggedUser.getUserId());
    }

    @PutMapping("/update")
    public void update(@RequestBody UpdateProjectEnvironmentForm form) {
        service.update(form);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }

    @GetMapping("/find-all/{projectId}")
    public List<ProjectEnvironment> findAll(@PathVariable("projectId") String projectId) {
        return service.getAllEnvironments(projectId);
    }
}
