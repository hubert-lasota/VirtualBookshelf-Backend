package org.hl.wirtualnyregalbackend.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.dto.UserCredentialsRequest;
import org.hl.wirtualnyregalbackend.auth.dto.UserSignInResponse;
import org.hl.wirtualnyregalbackend.auth.entity.AuthorityName;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.auth.exception.InvalidCredentialsException;
import org.hl.wirtualnyregalbackend.auth.exception.UsernameAlreadyExistsException;
import org.hl.wirtualnyregalbackend.auth.jwt.JwtService;
import org.hl.wirtualnyregalbackend.user.UserCommandService;
import org.hl.wirtualnyregalbackend.user.UserDefaultConfigurer;
import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.UserQueryService;
import org.hl.wirtualnyregalbackend.user.dto.UserRequest;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@Service
@AllArgsConstructor
@Slf4j
class AuthorizationService {

    private final UserQueryService userQuery;
    private final UserCommandService userCommand;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDefaultConfigurer userDefaultConfigurer;


    @Transactional
    public UserSignInResponse registerUser(UserRequest userRequest, MultipartFile profilePicture) {
        String username = userRequest.username();
        log.info("User registration started for username: {}", username);
        checkIfUsernameExists(username);

        String encodedPassword = passwordEncoder.encode(userRequest.password());
        User user = new User(username, encodedPassword, AuthorityName.USER);
        user.setUserProfile(userCommand.createUserProfile(userRequest.profile(), profilePicture, user));
        userCommand.save(user);
        userDefaultConfigurer.configure(user);
        String jwt = jwtService.generateToken(user);
        log.info("User successfully registered: {}", user.getUsername());
        return mapToUserSignInResponse(user, jwt);
    }

    @Transactional
    public UserResponse updateUserCredentials(User user, UserCredentialsRequest credentials) {
        log.info("User credentials update started for userId: {} and request: {}", user.getId(), credentials);
        String username = credentials.username();
        if (username != null) {
            checkIfUsernameExists(username);
            user.setUsername(username);
        }

        String password = credentials.password();
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }

        log.info("User credentials successfully updated (userId:{})", user.getId());
        Locale locale = LocaleContextHolder.getLocale();
        return UserMapper.toUserResponse(user, locale);
    }

    public UserSignInResponse signIn(UserCredentialsRequest credentials) {
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
        return mapToUserSignInResponse(user, jwt);
    }

    private void checkIfUsernameExists(String username) {
        if (userQuery.existsByUsername(username)) {
            String mess = "Username: %s already exists.".formatted(username);
            log.info(mess);
            throw new UsernameAlreadyExistsException(mess);
        }
    }


    private UserSignInResponse mapToUserSignInResponse(User user, String jwt) {
        Locale locale = LocaleContextHolder.getLocale();
        return UserMapper.toUserSignInResponse(user, jwt, locale);
    }

}
