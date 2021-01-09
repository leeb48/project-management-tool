package com.pmt.prjmanagetool.services;

import com.pmt.prjmanagetool.domain.User;
import com.pmt.prjmanagetool.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findUserByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User Not Found");

        return user;
    }

    @Transactional
    public User loadUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return userOptional.get();
    }
}
