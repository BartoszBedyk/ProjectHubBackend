package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.LoggedUser;
import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/project-member")
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;
    private final LoggedUser loggedUser;

    public ProjectMemberController(ProjectMemberService projectMemberService, LoggedUser loggedUser) {
        this.projectMemberService = projectMemberService;
        this.loggedUser = loggedUser;
    }

    @PostMapping("/save")
    public ProjectMember create(@RequestBody CreateProjectMemberForm form) {
        return projectMemberService.save(form, loggedUser.getUserId());
    }

    @PutMapping("/update")
    public ProjectMember update(@RequestBody UpdateProjectMemberForm form) {
        return projectMemberService.update(form, loggedUser.getUserId());
    }

    @GetMapping("/find/{projectId}")
    public List<ProjectMember> search(@PathVariable("projectId") String projectId) {
        return projectMemberService.findAllByProjectId(projectId);
    }

    @DeleteMapping("/delete/{userId}/{projectId}")
    public void delete(@PathVariable("userId") String userId, @PathVariable("projectId") String projectId) {
        projectMemberService.remove(userId, projectId);
    }

}
