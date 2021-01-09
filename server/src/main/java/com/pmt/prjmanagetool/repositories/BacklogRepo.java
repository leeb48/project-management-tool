package com.pmt.prjmanagetool.repositories;

import com.pmt.prjmanagetool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepo extends CrudRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String projIdentifier);

}
