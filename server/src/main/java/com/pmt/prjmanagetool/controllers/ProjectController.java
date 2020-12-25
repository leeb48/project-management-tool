package com.pmt.prjmanagetool.controllers;

import com.pmt.prjmanagetool.domain.Project;
import com.pmt.prjmanagetool.services.MapValidationErrorService;
import com.pmt.prjmanagetool.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ProjectController.BASE_URL)
public class ProjectController {

    public static final String BASE_URL = "/api/project";

    private final ProjectService projectService;
    private final MapValidationErrorService mapValidationErrorService;

    public ProjectController(ProjectService projectService, MapValidationErrorService mapValidationErrorService) {
        this.projectService = projectService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping()
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Project savedProject = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {

        Project project = projectService.findProjectByIdentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects() {

        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectId) {
        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<String>(
                "Project with ID: '" + projectId.toUpperCase() + "' was deleted.", HttpStatus.OK
        );
    }
    

}

























