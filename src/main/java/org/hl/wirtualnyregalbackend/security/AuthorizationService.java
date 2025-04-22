package org.hl.wirtualnyregalbackend.security;

import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.security.jwt.JwtService;
import org.hl.wirtualnyregalbackend.security.model.Authority;
import org.hl.wirtualnyregalbackend.security.model.AuthorityName;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.hl.wirtualnyregalbackend.security.model.dto.UserCredentialsDto;
import org.hl.wirtualnyregalbackend.security.model.dto.UserSignInResponseDto;
import org.hl.wirtualnyregalbackend.user.UserDefaultConfigurer;
import org.hl.wirtualnyregalbackend.user.dao.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    public UserSignInResponseDto registerUser(UserCredentialsDto credentials) {
        if (userRepository.existsByUsername(credentials.username())) {
            throw new InvalidRequestException("Username: %s is already in database".formatted(credentials.username()));
        }

        Authority userRole = new Authority(AuthorityName.USER);
        String encodedPassword = passwordEncoder.encode(credentials.password());
        User user = new User(credentials.username(), encodedPassword, userRole);
        userRole.addUser(user);
        userRepository.save(user);
        userDefaultConfigurer.configure(user);
        String jwt = jwtService.generateToken(user);
        return new UserSignInResponseDto(user.getId(), user.getUsername(), jwt);
    }

    public UserSignInResponseDto signIn(UserCredentialsDto credentials) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());

        Authentication authResult;
        try {
            authResult = authenticationManager.authenticate(authToken);
        } catch (AuthenticationException exc) {
            throw new InvalidRequestException("Login failed due to invalid credentials.", HttpStatus.UNAUTHORIZED);
        }

        User user = (User) authResult.getPrincipal();

        String jwt = jwtService.generateToken(user);
        return new UserSignInResponseDto(user.getId(), user.getUsername(), jwt);
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
