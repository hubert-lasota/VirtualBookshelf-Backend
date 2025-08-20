package org.hl.wirtualnyregalbackend.auth;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.dto.UserCredentialsDto;
import org.hl.wirtualnyregalbackend.auth.dto.UserSignInResponse;
import org.hl.wirtualnyregalbackend.auth.entity.Authority;
import org.hl.wirtualnyregalbackend.auth.entity.AuthorityName;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.auth.jwt.JwtService;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.user.UserDefaultConfigurer;
import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.UserRepository;
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
@AllArgsConstructor
class AuthorizationService {

    private final static Logger log = LoggerFactory.getLogger(AuthorizationService.class);

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDefaultConfigurer userDefaultConfigurer;


    public UserSignInResponse registerUser(UserCredentialsDto credentials) {
        if (userRepository.existsByUsername(credentials.username())) {
            throw new InvalidRequestException("Username: %s is already in database".formatted(credentials.username()));
        }

        Authority userRole = new Authority(AuthorityName.USER);
        String encodedPassword = passwordEncoder.encode(credentials.password());
        User user = new User(credentials.username(), encodedPassword, userRole);
        userRole.setUser(user);
        userRepository.save(user);
        userDefaultConfigurer.configure(user);
        String jwt = jwtService.generateToken(user);
        return UserMapper.toUserSignInResponse(user, jwt);
    }

    public UserSignInResponse signIn(UserCredentialsDto credentials) {
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
        return UserMapper.toUserSignInResponse(user, jwt);
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
