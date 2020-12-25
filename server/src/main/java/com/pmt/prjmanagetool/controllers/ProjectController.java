package com.pmt.prjmanagetool.controllers;

import com.pmt.prjmanagetool.domain.Project;
import com.pmt.prjmanagetool.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProjectController.BASE_URL)
public class ProjectController {

    public static final String BASE_URL = "/api/project";

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping()
    public ResponseEntity<Project> createNewProject(@RequestBody Project project) {

        Project savedProject = projectService.saveOrUpdateProject(project);

        return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
    }

}
