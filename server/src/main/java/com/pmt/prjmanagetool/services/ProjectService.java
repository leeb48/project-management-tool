package com.pmt.prjmanagetool.services;

import com.pmt.prjmanagetool.domain.Project;
import com.pmt.prjmanagetool.repositories.ProjectRepo;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepo projectRepo;

    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public Project saveOrUpdateProject(Project project) {

        return projectRepo.save(project);
    }

}
