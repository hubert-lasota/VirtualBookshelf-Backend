package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.hl.wirtualnyregalbackend.infrastructure.security.jwt.JwtFacade;
import org.hl.wirtualnyregalbackend.infrastructure.user.UserRepository;
import org.hl.wirtualnyregalbackend.infrastructure.user.dto.UserRequest;
import org.hl.wirtualnyregalbackend.infrastructure.user.dto.UserResponse;
import org.hl.wirtualnyregalbackend.infrastructure.user.entity.Authority;
import org.hl.wirtualnyregalbackend.infrastructure.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorizationFacade {

    private final static Logger log = LoggerFactory.getLogger(AuthorizationFacade.class);

    private final UserRepository userRepository;
    private final JwtFacade jwtFacade;
    private final AuthenticationManager authenticationManager;

    public AuthorizationFacade(UserRepository userRepository, JwtFacade jwtFacade, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtFacade = jwtFacade;
        this.authenticationManager = authenticationManager;
    }

    public UserResponse createUser(UserRequest request) {
        if(userRepository.existsByUsername(request.username())) {
            log.debug("Username: %s already exists in database!".formatted(request.username()));
            return null;
        }

        Authority userRole = new Authority("USER");
        User user = new User(request.username(), request.password(), userRole);
        userRole.setUser(user);
        userRepository.save(user);

        String jwt = jwtFacade.generateToken(user);
        return new UserResponse(user.getId(), user.getUsername(), jwt);
    }

    public UserResponse signIn(UserRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.username());
        if(userOpt.isEmpty()) {
            log.debug("Username: %s already exists in database!".formatted(request.username()));
            return null;
        }
        User user = userOpt.get();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        String jwt = jwtFacade.generateToken(user);
        return new UserResponse(user.getId(), user.getUsername(), jwt);
    }


}
