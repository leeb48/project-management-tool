package com.pmt.prjmanagetool.repositories;

import com.pmt.prjmanagetool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends CrudRepository<Project, Long> {

    Project findProjectByProjectIdentifier(String projIdentifier);


}
