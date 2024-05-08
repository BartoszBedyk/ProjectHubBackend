package com.sensilabs.projecthub.project;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/search")
    public SearchResponse<ProjectMember> search(@RequestBody SearchForm searchForm) {
        return projectMemberService.search(searchForm);
    }

    @DeleteMapping("/delete/{id}")
    public void get(@PathVariable("id") String id) {
        projectMemberService.remove(id);
    }

}
