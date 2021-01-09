package com.pmt.prjmanagetool.repositories;

import com.pmt.prjmanagetool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    User findUserByUsername(String username);

}
