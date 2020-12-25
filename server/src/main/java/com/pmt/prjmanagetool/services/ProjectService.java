package com.pmt.prjmanagetool.services;

import com.pmt.prjmanagetool.domain.Project;
import com.pmt.prjmanagetool.exceptions.ProjectIdException;
import com.pmt.prjmanagetool.exceptions.ProjectNotFoundException;
import com.pmt.prjmanagetool.repositories.ProjectRepo;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepo projectRepo;

    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public Project saveOrUpdateProject(Project project) {

        try {

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

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

    public Project findProjectByIdentifier(String projIdentifier) {

        Project project = projectRepo.findProjectByProjectIdentifier(projIdentifier.toUpperCase());

        if (project == null) {
            throw new ProjectNotFoundException(
                    "Project with identifier '" + projIdentifier.toUpperCase() + "' not found."
            );
        }

        return project;
    }

    public Iterable<Project> findAllProjects() {

        return projectRepo.findAll();
    }

    public void deleteProjectByIdentifier(String projIdentifier) {
        Project project = findProjectByIdentifier(projIdentifier.toUpperCase());

        projectRepo.delete(project);
    }


}

































