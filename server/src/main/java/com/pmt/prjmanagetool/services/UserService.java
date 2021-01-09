package com.pmt.prjmanagetool.services;

import com.pmt.prjmanagetool.domain.User;
import com.pmt.prjmanagetool.exceptions.DuplicateUsernameException;
import com.pmt.prjmanagetool.exceptions.GeneralException;
import com.pmt.prjmanagetool.repositories.UserRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveUser(User newUser) {

        try {

            // hash password
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            // username has to be unique

            // make sure that password and confirmPassword match

            // we don't persist or show the confirmPassword
            return userRepo.save(newUser);

        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUsernameException("Username already exists");
        } catch (Exception e) {
            throw new GeneralException("Exception Thrown at UserService saveUser method");
        }

    }
}
