package com.pmt.prjmanagetool.repositories;

import com.pmt.prjmanagetool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepo extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String projIdentifier);

    ProjectTask findByProjectSequence(String projSequence);

}
