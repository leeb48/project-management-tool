package com.pmt.prjmanagetool.services;

import com.pmt.prjmanagetool.domain.Backlog;
import com.pmt.prjmanagetool.domain.Project;
import com.pmt.prjmanagetool.domain.User;
import com.pmt.prjmanagetool.exceptions.ProjectIdException;
import com.pmt.prjmanagetool.exceptions.ProjectNotFoundException;
import com.pmt.prjmanagetool.repositories.BacklogRepo;
import com.pmt.prjmanagetool.repositories.ProjectRepo;
import com.pmt.prjmanagetool.repositories.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;
    private final BacklogRepo backlogRepo;

    public ProjectService(ProjectRepo projectRepo, UserRepo userRepo, BacklogRepo backlogRepo) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
        this.backlogRepo = backlogRepo;
    }

    public Project saveOrUpdateProject(Project project, String username) {

        try {

            // check to see if the authenticated user owns the project
            // that is being updated
            if (project.getId() != null) {
                Project existingProj = findProjectByIdentifier(project.getProjectIdentifier(), username);

                if (!existingProj.getId().equals(project.getId())) {
                    throw new ProjectNotFoundException(
                            "Existing project id(" + existingProj.getId() + ") and update project id(" +
                                    project.getId() + ") mismatch");
                }
            }

            User user = userRepo.findUserByUsername(username);

            // set project - user relationship
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            // create a new backlog only when a new project is created
            if (project.getId() == null) {
                Backlog backlog = new Backlog();

                // set relationship
                project.setBacklog(backlog);
                backlog.setProject(project);

                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            // backlog needs to be set when project update occurs
            if (project.getId() != null) {
                Backlog foundBacklog = backlogRepo.findByProjectIdentifier(project.getProjectIdentifier());
                project.setBacklog(foundBacklog);
            }

            return projectRepo.save(project);

        } catch (Exception e) {

            if (e.toString().contains("ConstraintViolationException")) {
                throw new ProjectIdException(
                        "Project ID: " + project.getProjectIdentifier().toUpperCase() + " already exists."
                );
            }
            throw new RuntimeException(e);
        }
    }

    public Project findProjectByIdentifier(String projIdentifier, String username) {

        Project project = projectRepo.findProjectByProjectIdentifier(projIdentifier.toUpperCase());

        if (project == null) {
            throw new ProjectNotFoundException(
                    "Project with identifier '" + projIdentifier.toUpperCase() + "' not found."
            );
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException(
                    "Project not found in your account"
            );
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username) {


        return projectRepo.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projIdentifier, String username) {
        Project project = findProjectByIdentifier(projIdentifier.toUpperCase(), username);

        projectRepo.delete(project);
    }


}

































