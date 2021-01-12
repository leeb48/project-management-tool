package com.pmt.prjmanagetool.services;

import com.google.common.collect.Iterables;
import com.pmt.prjmanagetool.domain.Backlog;
import com.pmt.prjmanagetool.domain.ProjectTask;
import com.pmt.prjmanagetool.exceptions.ProjectBacklogNotFoundException;
import com.pmt.prjmanagetool.exceptions.ProjectNotFoundException;
import com.pmt.prjmanagetool.repositories.BacklogRepo;
import com.pmt.prjmanagetool.repositories.ProjectTaskRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProjectTaskService {

    private final ProjectService projectService;
    private final ProjectTaskRepo projectTaskRepo;
    private final BacklogRepo backlogRepo;

    public ProjectTaskService(ProjectService projectService, ProjectTaskRepo projectTaskRepo, BacklogRepo backlogRepo) {
        this.projectService = projectService;
        this.projectTaskRepo = projectTaskRepo;
        this.backlogRepo = backlogRepo;
    }

    @Transactional
    public ProjectTask addProjectTask(String projIdentifier, ProjectTask projectTask, String username) {


        // project tasks to be added to a specific project (to an existing backlog)
        Backlog backlog = projectService.findProjectByIdentifier(projIdentifier, username).getBacklog();


        // set the backlog to project task (sets the relationship)
        projectTask.setBacklog(backlog);

        // project task sequence needs to be incremented based on the order they were added to the project
        Integer backlogPTSeq = backlog.getPTSequence();

        // before we persist the task, update the backlog sequence
        backlog.setPTSequence(++backlogPTSeq);

        // add sequence to project task
        projectTask.setProjectSequence(projIdentifier + "-" + backlogPTSeq);
        projectTask.setProjectIdentifier(projIdentifier);


        // set initial priority when priority is null
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }

        // set initial status when status is null
        if (projectTask.getStatus() == null || projectTask.getStatus().equals("")) {
            projectTask.setStatus("TODO");
        }

        return projectTaskRepo.save(projectTask);

    }

    public Iterable<ProjectTask> findBacklogById(String projIdentifier, String username) {

        // security check to verify that authenticated user owns the project and backlog with
        // given project identifier
        projectService.findProjectByIdentifier(projIdentifier, username);

        Iterable<ProjectTask> tasks = projectTaskRepo.findByProjectIdentifierOrderByPriority(projIdentifier);
        if (Iterables.size(tasks) == 0) {
            throw new ProjectBacklogNotFoundException("Backlog with identifier '" + projIdentifier + "' does not exist.");
        }
        return tasks;
    }

    public ProjectTask findPTByProjSequence(String backlog_id, String pt_id, String username) {

        backlog_id = backlog_id.toUpperCase();
        pt_id = pt_id.toUpperCase();

        // security check to verify that project with backlog_id exists
        // and it belongs to the authenticated user
        projectService.findProjectByIdentifier(backlog_id, username);

        // make sure that our task exists
        ProjectTask projectTask = projectTaskRepo.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project task with sequence '" + pt_id + "' does not exist.");
        }

        // make sure that the backlog/project id in the path corresponds to the right project
        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException(
                    "Project task '" + pt_id + "' does not exists in project '" + backlog_id + "'."
            );
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {

        ProjectTask projectTask = findPTByProjSequence(backlog_id, pt_id, username);

        updatedTask.setId(projectTask.getId());

        return projectTaskRepo.save(updatedTask);
    }

    public void deleteProjectTaskBySequence(String backlog_id, String pt_id, String username) {

        ProjectTask projectTask = findPTByProjSequence(backlog_id, pt_id, username);


        projectTaskRepo.delete(projectTask);
    }

}















