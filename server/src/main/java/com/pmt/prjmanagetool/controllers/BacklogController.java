package com.pmt.prjmanagetool.controllers;

import com.pmt.prjmanagetool.domain.ProjectTask;
import com.pmt.prjmanagetool.services.MapValidationErrorService;
import com.pmt.prjmanagetool.services.ProjectTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(BacklogController.BASE_URL)
@CrossOrigin
public class BacklogController {

    public static final String BASE_URL = "/api/backlog";

    private final ProjectTaskService projectTaskService;
    private final MapValidationErrorService mapValidationErrorService;

    public BacklogController(ProjectTaskService projectTaskService,
                             MapValidationErrorService mapValidationErrorService) {
        this.projectTaskService = projectTaskService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask,
                                            BindingResult result,
                                            @PathVariable String backlog_id,
                                            Principal principal) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask savedProjectTask = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

        return new ResponseEntity<ProjectTask>(savedProjectTask, HttpStatus.CREATED);
    }

    @GetMapping("/{projIdentifier}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<ProjectTask> getAllProjectTasks(@PathVariable String projIdentifier, Principal principal) {

        return projectTaskService.findBacklogById(projIdentifier, principal.getName());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id,
                                            @PathVariable String pt_id,
                                            Principal principal) {

        ProjectTask projectTask = projectTaskService.findPTByProjSequence(backlog_id, pt_id, principal.getName());

        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                               @PathVariable String backlog_id, @PathVariable String pt_id,
                                               Principal principal) {


        if (result.hasErrors()) {
            return mapValidationErrorService.MapValidationService(result);
        }

        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask,
                backlog_id, pt_id,
                principal.getName());


        return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
    }


    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id,
                                               @PathVariable String pt_id,
                                               Principal principal) {

        projectTaskService.deleteProjectTaskBySequence(backlog_id, pt_id, principal.getName());

        return new ResponseEntity<String>(
                "Project task with identifier '" + pt_id + "' was deleted.", HttpStatus.OK
        );

    }

}













