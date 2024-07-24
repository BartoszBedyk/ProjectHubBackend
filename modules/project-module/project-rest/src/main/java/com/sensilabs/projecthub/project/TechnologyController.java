package com.sensilabs.projecthub.project;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("technology")
public class TechnologyController {
    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @PostMapping("/create")
    public Technology create(@RequestBody CreateTechnologyForm form) {
        return technologyService.create(form);
    }

    @GetMapping("/find-all")
    public List<Technology> findAll() {
        return technologyService.findAll();
    }

    @GetMapping("/{id}")
    public Technology getById(@PathVariable("id") String id) { return technologyService.get(id);}
}
