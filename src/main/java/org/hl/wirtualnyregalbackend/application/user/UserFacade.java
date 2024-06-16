package org.hl.wirtualnyregalbackend.application.user;

import org.hl.wirtualnyregalbackend.infrastructure.user.UserRepository;
import org.hl.wirtualnyregalbackend.infrastructure.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserFacade implements UserDetailsService {

    private final static Logger log = LoggerFactory.getLogger(UserFacade.class);

    private final UserRepository userRepository;

    public UserFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Username: %s not found in database!".formatted(username));
        }
        return userOpt.get();
    }



}
