package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.hl.wirtualnyregalbackend.infrastructure.security.dto.LoginRequest;
import org.hl.wirtualnyregalbackend.infrastructure.security.dto.LoginResponse;
import org.hl.wirtualnyregalbackend.infrastructure.security.exception.InvalidSignInCredentialsException;
import org.hl.wirtualnyregalbackend.infrastructure.security.exception.UsernameAlreadyExistsException;
import org.hl.wirtualnyregalbackend.infrastructure.security.jwt.JwtService;
import org.hl.wirtualnyregalbackend.infrastructure.user.UserDefaultConfigurer;
import org.hl.wirtualnyregalbackend.infrastructure.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class AuthorizationService {

    private final static Logger log = LoggerFactory.getLogger(AuthorizationService.class);

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDefaultConfigurer userDefaultConfigurer;

    AuthorizationService(UserRepository userRepository,
                         JwtService jwtService,
                         AuthenticationManager authenticationManager,
                         PasswordEncoder passwordEncoder,
                         UserDefaultConfigurer userDefaultConfigurer) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDefaultConfigurer = userDefaultConfigurer;
    }

    public LoginResponse registerUser(LoginRequest request) {
        if(userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException("Username: %s is already in database"
                    .formatted(request.username()));
        }

        Authority userRole = new Authority("USER");
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.username(), encodedPassword, userRole);
        userRole.setUser(user);
        userRepository.save(user);
        userDefaultConfigurer.configure(user);
        String jwt = jwtService.generateToken(user);
        return new LoginResponse(user.getId(), user.getUsername(), jwt);
    }

    public LoginResponse signIn(LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.username(), request.password());

        Authentication authResult;
        try {
            authResult = authenticationManager.authenticate(authToken);
        } catch (AuthenticationException exc) {
            throw new InvalidSignInCredentialsException();
        }

        User user = (User) authResult.getPrincipal();

        String jwt = jwtService.generateToken(user);
        return new LoginResponse(user.getId(), user.getUsername(), jwt);
    }

    public boolean isJwtValid(String jwt) {
        try {
            String username = jwtService.extractUsername(jwt);
            UserDetails userDetails = userRepository.findByUsername(username).orElseThrow();
            return jwtService.isTokenValid(jwt, userDetails);
        } catch (RuntimeException e) {
            return false;
        }
    }

}
