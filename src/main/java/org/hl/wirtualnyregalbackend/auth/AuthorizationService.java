package org.hl.wirtualnyregalbackend.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.dto.UserSignInRequest;
import org.hl.wirtualnyregalbackend.auth.dto.UserSignInResponse;
import org.hl.wirtualnyregalbackend.auth.entity.AuthorityName;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.auth.exception.InvalidCredentialsException;
import org.hl.wirtualnyregalbackend.auth.exception.UsernameAlreadyExistsException;
import org.hl.wirtualnyregalbackend.auth.jwt.JwtService;
import org.hl.wirtualnyregalbackend.user.UserDefaultConfigurer;
import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.UserService;
import org.hl.wirtualnyregalbackend.user.dto.UserRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Slf4j
class AuthorizationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDefaultConfigurer userDefaultConfigurer;


    @Transactional
    public UserSignInResponse registerUser(UserRequest userRequest, MultipartFile profilePicture) {
        String username = userRequest.username();
        log.info("User registration started for username: {}", username);
        if (userService.existsByUsername(username)) {
            log.warn("User registration failed - username already exists: {}", username);
            throw new UsernameAlreadyExistsException("Username: %s already exists.".formatted(username));
        }

        String encodedPassword = passwordEncoder.encode(userRequest.password());
        User user = new User(username, encodedPassword, AuthorityName.USER);
        user.setUserProfile(userService.createUserProfile(userRequest.profile(), profilePicture, user));
        userService.save(user);
        userDefaultConfigurer.configure(user);
        String jwt = jwtService.generateToken(user);
        log.info("User successfully registered: {}", user.getUsername());
        return UserMapper.toUserSignInResponse(user, jwt);
    }

    public UserSignInResponse signIn(UserSignInRequest credentials) {
        log.info("User sign-in attempt for username: {}", credentials.username());
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());

        Authentication authResult;
        try {
            authResult = authenticationManager.authenticate(authToken);
        } catch (AuthenticationException exc) {
            log.warn("User sign-in failed due to invalid credentials: {}", credentials.username());
            throw new InvalidCredentialsException("Sign in failed due to invalid credentials.");
        }

        User user = (User) authResult.getPrincipal();

        String jwt = jwtService.generateToken(user);
        log.info("User successfully signed in: {}", user.getUsername());
        return UserMapper.toUserSignInResponse(user, jwt);
    }

}
