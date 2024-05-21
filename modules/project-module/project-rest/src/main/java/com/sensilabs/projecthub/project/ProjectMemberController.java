package com.sensilabs.projecthub.project;

import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/project-member")
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @PostMapping("/save")
    public ProjectMember create(@RequestBody CreateProjectMemberForm form) {
        return projectMemberService.save(form, "mockUser");
    }

    @PutMapping("/update")
    public ProjectMember update(@RequestBody UpdateProjectMemberForm form) {
        return projectMemberService.update(form);
    }

    @GetMapping("/search/{id}")
    public List<ProjectDTO> searchProjects(@PathVariable("id") String userId) {
        return projectMemberService.getProjects(userId);
    }

    @DeleteMapping("/delete/{userId}/{projectId}")
    public void delete(@PathVariable("userId") String userId, @PathVariable("projectId") String projectId) {
        projectMemberService.remove(userId, projectId);
    }

}
